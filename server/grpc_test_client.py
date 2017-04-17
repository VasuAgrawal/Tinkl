from tinkl_pb2 import PuckId
import tinkl_pb2_grpc

import grpc

channel = grpc.insecure_channel("localhost:5000")
stub = tinkl_pb2_grpc.TinklStub(channel)
response = stub.getUrination(PuckId(hub_id=1, sensor_node_id=3))
print(response)
