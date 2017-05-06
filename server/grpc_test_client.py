from tinkl_pb2 import PuckId
import tinkl_pb2_grpc

import grpc

channel = grpc.insecure_channel("olympus.wv.cc.cmu.edu:5000")
stub = tinkl_pb2_grpc.TinklStub(channel)
response = stub.getUrination(PuckId(hub_id=1, sensor_node_id=1))
print("Average Reading:")
print(response)
print("-------------------------------------------------")

response = stub.getAllUrination(PuckId(hub_id=1, sensor_node_id=1))
print("Individual Readings:")
for i, sample in enumerate(response):
    print("Reading %d" % i)
    print(sample)
