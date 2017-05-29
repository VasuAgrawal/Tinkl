from concurrent import futures
import grpc
import time

# import tinkl_pb2
from tinkl_pb2 import Sample
from tinkl_pb2 import Color
import tinkl_pb2_grpc

class TinklServer(tinkl_pb2_grpc.TinklServicer):
    def getUrination(self, request, context):
        return Sample(sample_id=1, temperature=98.6, turbidity=200,
                      color=Color(r=255, g=255, b=255))

    def getAllUrination(self, request, context):
        pass

def main():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    tinkl_pb2_grpc.add_TinklServicer_to_server(TinklServer(), server)
    server.add_insecure_port('localhost:4242')
    server.start()

    try:
        while True:
            time.sleep(1000)
    except KeyboardInterrupt:
        server.stop(0)

if __name__ == "__main__":
    main()
