package com.tinkl.tinkl;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: tinkl.proto")
public final class TinklGrpc {

  private TinklGrpc() {}

  public static final String SERVICE_NAME = "routeguide.Tinkl";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.tinkl.tinkl.PuckId,
      com.tinkl.tinkl.Sample> METHOD_GET_URINATION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "routeguide.Tinkl", "getUrination"),
          io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(com.tinkl.tinkl.PuckId.getDefaultInstance()),
          io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(com.tinkl.tinkl.Sample.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.tinkl.tinkl.PuckId,
      com.tinkl.tinkl.Sample> METHOD_GET_ALL_URINATION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "routeguide.Tinkl", "getAllUrination"),
          io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(com.tinkl.tinkl.PuckId.getDefaultInstance()),
          io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(com.tinkl.tinkl.Sample.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TinklStub newStub(io.grpc.Channel channel) {
    return new TinklStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TinklBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TinklBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static TinklFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TinklFutureStub(channel);
  }

  /**
   */
  public static abstract class TinklImplBase implements io.grpc.BindableService {

    /**
     */
    public void getUrination(com.tinkl.tinkl.PuckId request,
        io.grpc.stub.StreamObserver<com.tinkl.tinkl.Sample> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_URINATION, responseObserver);
    }

    /**
     */
    public void getAllUrination(com.tinkl.tinkl.PuckId request,
        io.grpc.stub.StreamObserver<com.tinkl.tinkl.Sample> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_URINATION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_URINATION,
            asyncUnaryCall(
              new MethodHandlers<
                com.tinkl.tinkl.PuckId,
                com.tinkl.tinkl.Sample>(
                  this, METHODID_GET_URINATION)))
          .addMethod(
            METHOD_GET_ALL_URINATION,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.tinkl.tinkl.PuckId,
                com.tinkl.tinkl.Sample>(
                  this, METHODID_GET_ALL_URINATION)))
          .build();
    }
  }

  /**
   */
  public static final class TinklStub extends io.grpc.stub.AbstractStub<TinklStub> {
    private TinklStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TinklStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TinklStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TinklStub(channel, callOptions);
    }

    /**
     */
    public void getUrination(com.tinkl.tinkl.PuckId request,
        io.grpc.stub.StreamObserver<com.tinkl.tinkl.Sample> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_URINATION, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllUrination(com.tinkl.tinkl.PuckId request,
        io.grpc.stub.StreamObserver<com.tinkl.tinkl.Sample> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_ALL_URINATION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TinklBlockingStub extends io.grpc.stub.AbstractStub<TinklBlockingStub> {
    private TinklBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TinklBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TinklBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TinklBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.tinkl.tinkl.Sample getUrination(com.tinkl.tinkl.PuckId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_URINATION, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.tinkl.tinkl.Sample> getAllUrination(
        com.tinkl.tinkl.PuckId request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_ALL_URINATION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TinklFutureStub extends io.grpc.stub.AbstractStub<TinklFutureStub> {
    private TinklFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TinklFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TinklFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TinklFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.tinkl.tinkl.Sample> getUrination(
        com.tinkl.tinkl.PuckId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_URINATION, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_URINATION = 0;
  private static final int METHODID_GET_ALL_URINATION = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TinklImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TinklImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_URINATION:
          serviceImpl.getUrination((com.tinkl.tinkl.PuckId) request,
              (io.grpc.stub.StreamObserver<com.tinkl.tinkl.Sample>) responseObserver);
          break;
        case METHODID_GET_ALL_URINATION:
          serviceImpl.getAllUrination((com.tinkl.tinkl.PuckId) request,
              (io.grpc.stub.StreamObserver<com.tinkl.tinkl.Sample>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TinklGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(METHOD_GET_URINATION)
              .addMethod(METHOD_GET_ALL_URINATION)
              .build();
        }
      }
    }
    return result;
  }
}
