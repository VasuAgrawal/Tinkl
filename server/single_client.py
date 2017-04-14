#!/usr/bin/env python3

# Send a single message to the server to test that it works.
import logging
import random

import tornado
import tornado.tcpclient

from tinkl_pb2 import Color
from tinkl_pb2 import Sample
from tinkl_pb2 import Urination

import packet

@tornado.gen.coroutine
def my_callback(stream):
    iter_num = 0
    while True:
        message = Urination()
        message.hub_id = 1
        message.sensor_node_id = random.randint(1, 10)
        message.urination_id = iter_num
        message.sensor_battery_voltage = random.random() * 2 + 2

        color = Color()
        sample = Sample()

        for i in range(random.randint(5, 10)):
            color.r = random.randint(0, 2**16 - 1)
            color.g = random.randint(0, 2**16 - 1)
            color.b = random.randint(0, 2**16 - 1)
            
            sample.sample_id = i
            sample.temperature = random.random() * 10 + 90
            sample.turbidity = random.randint(100, 200)
            sample.color.CopyFrom(color)
            
            message.samples.extend([sample])

        logging.info("Writing message to sever:\n%s", message)
        data_bytes = packet.make_packet_from_bytes(message.SerializeToString())
        stream.write(data_bytes)

        yield tornado.gen.sleep(1)



def main():
    logging.getLogger().setLevel(logging.DEBUG)
    logging.info("Connecting to server.")
    client = tornado.tcpclient.TCPClient()
    client.connect("localhost", 8888, callback=my_callback)
    tornado.ioloop.IOLoop.instance().start()

if __name__ == "__main__":
    main()
