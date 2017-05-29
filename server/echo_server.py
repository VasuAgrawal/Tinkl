#!/usr/bin/env python3
# System imports
import logging
import sys

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

# Custom imports
import packet


class DataServer(tornado.tcpserver.TCPServer):
    
    @tornado.gen.coroutine
    def handle_stream(self, stream, address):
        logging.debug("Opened a stream from %s", address)

        while True:
            try:
                message_bytes = yield packet.read_packet_from_stream(stream)
                message = Urination()
                message.ParseFromString(message_bytes)
                logging.debug("Server received message from %s:\n%s",
                        address, message)

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

    try:
        PORT = int(sys.argv[1])
    except:
        PORT = 8888


    logging.info("Echo server starting to listen on %d", PORT)
    DataServer().listen(PORT)

    tornado.ioloop.IOLoop.current().start()
