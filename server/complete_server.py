#!/usr/bin/env python3
# System imports
import logging
import sys
import argparse
import threading
from concurrent import futures
import time
import copy

# Tornado library imports
import tornado
import tornado.gen
import tornado.iostream
import tornado.tcpserver

# Proto imports
import google.protobuf.message
from tinkl_pb2 import Color
from tinkl_pb2 import Sample
from tinkl_pb2 import Urination
import tinkl_pb2_grpc
import grpc

# Custom imports
import packet

parser = argparse.ArgumentParser(description="Tinkl server.")
parser.add_argument('--grpc-port', dest='grpc_port', default=5000, type=int,
                    help="Port number for the gRPC server")
parser.add_argument('--data-port', dest='data_port', default=5001, type=int,
                    help="Port number for the data server")
args = parser.parse_args()


urination_data = dict()
urination_data_lock = threading.Lock()

class TinklServer(tinkl_pb2_grpc.TinklServicer):
    def getUrination(self, request, context):
        global urination_data
        global urination_data_lock

        logging.info("gRPC Server received a request!")

        with urination_data_lock:
            key = (request.hub_id, request.sensor_node_id)
            if key in urination_data:
                timestamp, urination = copy.deepcopy(urination_data[key])
            else:
                logging.warning("Requested key not in data set: %s", key)
                return Sample()

            FIVE_MINUTES = 60 * 5
            if time.time() - timestamp < FIVE_MINUTES:
                out = Sample()
                for i, sample in enumerate(urination.samples):
                    out.temperature = (
                        (out.temperature * i + sample.temperature) / (i+1))
                    out.turbidity = (
                        (out.turbidity * i + sample.turbidity) // (i+1))
                    out.color.r = (
                        (out.color.r * i + sample.color.r) // (i+1))
                    out.color.g = (
                        (out.color.g * i + sample.color.g) // (i+1))
                    out.color.b = (
                        (out.color.b * i + sample.color.b) // (i+1))

                # Return actually valid data.
                return out

            else:
                logging.warning("Requested key %s has expired. "
                        "Original timestamp %f, current %f.", key, timestamp,
                        time.time())
                return Sample()

        return Sample()


    def getAllUrination(self, request, context):
        return Sample()


class DataServer(tornado.tcpserver.TCPServer):
    
    @tornado.gen.coroutine
    def handle_stream(self, stream, address):
        logging.debug("Opened a stream from %s", address)

        global urination_data
        global urination_data_lock
        while True:
            try:
                message_bytes = yield packet.read_packet_from_stream(stream)
                message = Urination()
                message.ParseFromString(message_bytes)
                logging.debug("Server received message from %s:\n%s",
                        address, message)

                if len(message.samples) == 0:
                    logging.warning("Received no samples from %s:\n%s",
                            address, message)
              
                with urination_data_lock:
                    key = (message.hub_id, message.sensor_node_id)
                    urination_data[key] = (time.time(), message)
                    logging.info("Adding urination with key %s to data", key)

            except tornado.iostream.StreamClosedError:
                logging.info("Closed stream from %s", address)
                return
            except google.protobuf.message.DecodeError:
                logging.warning("Broken message read from %s "
                                "terminating connection!", address)
                stream.close()
                return


if __name__ == "__main__":
    logging.getLogger().setLevel(logging.DEBUG)

    logging.debug("Starting gRPC thread pool.")
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    tinkl_pb2_grpc.add_TinklServicer_to_server(TinklServer(), server)
    server.add_insecure_port("localhost:%d" % args.grpc_port)
    logging.debug("Starting gRPC server on port %d.", args.grpc_port)
    server.start()

    logging.debug("Starting data server on port %d.", args.data_port)
    DataServer().listen(args.data_port)

    try:
        tornado.ioloop.IOLoop.current().start()
    except KeyboardInterrupt:
        logging.warning("Keyboard interrupt! Shutting down servers.")
        tornado.ioloop.IOLoop.current().stop()
        server.stop(0)
