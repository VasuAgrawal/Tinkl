# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: tinkl.proto

import sys
_b=sys.version_info[0]<3 and (lambda x:x) or (lambda x:x.encode('latin1'))
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='tinkl.proto',
  package='',
  syntax='proto3',
  serialized_pb=_b('\n\x0btinkl.proto\"(\n\x05\x43olor\x12\t\n\x01r\x18\x01 \x01(\r\x12\t\n\x01g\x18\x02 \x01(\r\x12\t\n\x01\x62\x18\x03 \x01(\r\"Z\n\x06Sample\x12\x11\n\tsample_id\x18\x01 \x01(\x05\x12\x13\n\x0btemperature\x18\x02 \x01(\x02\x12\x11\n\tturbidity\x18\x03 \x01(\x05\x12\x15\n\x05\x63olor\x18\x04 \x01(\x0b\x32\x06.Color\"\x83\x01\n\tUrination\x12\x0e\n\x06hub_id\x18\x01 \x01(\x04\x12\x16\n\x0esensor_node_id\x18\x02 \x01(\x04\x12\x14\n\x0curination_id\x18\x03 \x01(\x04\x12\x1e\n\x16sensor_battery_voltage\x18\x04 \x01(\x02\x12\x18\n\x07samples\x18\x05 \x03(\x0b\x32\x07.Sample\"0\n\x06PuckId\x12\x0e\n\x06hub_id\x18\x01 \x01(\x04\x12\x16\n\x0esensor_node_id\x18\x02 \x01(\x04\x32T\n\x05Tinkl\x12\"\n\x0cgetUrination\x12\x07.PuckId\x1a\x07.Sample\"\x00\x12\'\n\x0fgetAllUrination\x12\x07.PuckId\x1a\x07.Sample\"\x00\x30\x01\x62\x06proto3')
)
_sym_db.RegisterFileDescriptor(DESCRIPTOR)




_COLOR = _descriptor.Descriptor(
  name='Color',
  full_name='Color',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='r', full_name='Color.r', index=0,
      number=1, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='g', full_name='Color.g', index=1,
      number=2, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='b', full_name='Color.b', index=2,
      number=3, type=13, cpp_type=3, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=15,
  serialized_end=55,
)


_SAMPLE = _descriptor.Descriptor(
  name='Sample',
  full_name='Sample',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='sample_id', full_name='Sample.sample_id', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='temperature', full_name='Sample.temperature', index=1,
      number=2, type=2, cpp_type=6, label=1,
      has_default_value=False, default_value=float(0),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='turbidity', full_name='Sample.turbidity', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='color', full_name='Sample.color', index=3,
      number=4, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=57,
  serialized_end=147,
)


_URINATION = _descriptor.Descriptor(
  name='Urination',
  full_name='Urination',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='hub_id', full_name='Urination.hub_id', index=0,
      number=1, type=4, cpp_type=4, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='sensor_node_id', full_name='Urination.sensor_node_id', index=1,
      number=2, type=4, cpp_type=4, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='urination_id', full_name='Urination.urination_id', index=2,
      number=3, type=4, cpp_type=4, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='sensor_battery_voltage', full_name='Urination.sensor_battery_voltage', index=3,
      number=4, type=2, cpp_type=6, label=1,
      has_default_value=False, default_value=float(0),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='samples', full_name='Urination.samples', index=4,
      number=5, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=150,
  serialized_end=281,
)


_PUCKID = _descriptor.Descriptor(
  name='PuckId',
  full_name='PuckId',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='hub_id', full_name='PuckId.hub_id', index=0,
      number=1, type=4, cpp_type=4, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='sensor_node_id', full_name='PuckId.sensor_node_id', index=1,
      number=2, type=4, cpp_type=4, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=283,
  serialized_end=331,
)

_SAMPLE.fields_by_name['color'].message_type = _COLOR
_URINATION.fields_by_name['samples'].message_type = _SAMPLE
DESCRIPTOR.message_types_by_name['Color'] = _COLOR
DESCRIPTOR.message_types_by_name['Sample'] = _SAMPLE
DESCRIPTOR.message_types_by_name['Urination'] = _URINATION
DESCRIPTOR.message_types_by_name['PuckId'] = _PUCKID

Color = _reflection.GeneratedProtocolMessageType('Color', (_message.Message,), dict(
  DESCRIPTOR = _COLOR,
  __module__ = 'tinkl_pb2'
  # @@protoc_insertion_point(class_scope:Color)
  ))
_sym_db.RegisterMessage(Color)

Sample = _reflection.GeneratedProtocolMessageType('Sample', (_message.Message,), dict(
  DESCRIPTOR = _SAMPLE,
  __module__ = 'tinkl_pb2'
  # @@protoc_insertion_point(class_scope:Sample)
  ))
_sym_db.RegisterMessage(Sample)

Urination = _reflection.GeneratedProtocolMessageType('Urination', (_message.Message,), dict(
  DESCRIPTOR = _URINATION,
  __module__ = 'tinkl_pb2'
  # @@protoc_insertion_point(class_scope:Urination)
  ))
_sym_db.RegisterMessage(Urination)

PuckId = _reflection.GeneratedProtocolMessageType('PuckId', (_message.Message,), dict(
  DESCRIPTOR = _PUCKID,
  __module__ = 'tinkl_pb2'
  # @@protoc_insertion_point(class_scope:PuckId)
  ))
_sym_db.RegisterMessage(PuckId)


try:
  # THESE ELEMENTS WILL BE DEPRECATED.
  # Please use the generated *_pb2_grpc.py files instead.
  import grpc
  from grpc.framework.common import cardinality
  from grpc.framework.interfaces.face import utilities as face_utilities
  from grpc.beta import implementations as beta_implementations
  from grpc.beta import interfaces as beta_interfaces


  class TinklStub(object):

    def __init__(self, channel):
      """Constructor.

      Args:
        channel: A grpc.Channel.
      """
      self.getUrination = channel.unary_unary(
          '/Tinkl/getUrination',
          request_serializer=PuckId.SerializeToString,
          response_deserializer=Sample.FromString,
          )
      self.getAllUrination = channel.unary_stream(
          '/Tinkl/getAllUrination',
          request_serializer=PuckId.SerializeToString,
          response_deserializer=Sample.FromString,
          )


  class TinklServicer(object):

    def getUrination(self, request, context):
      context.set_code(grpc.StatusCode.UNIMPLEMENTED)
      context.set_details('Method not implemented!')
      raise NotImplementedError('Method not implemented!')

    def getAllUrination(self, request, context):
      context.set_code(grpc.StatusCode.UNIMPLEMENTED)
      context.set_details('Method not implemented!')
      raise NotImplementedError('Method not implemented!')


  def add_TinklServicer_to_server(servicer, server):
    rpc_method_handlers = {
        'getUrination': grpc.unary_unary_rpc_method_handler(
            servicer.getUrination,
            request_deserializer=PuckId.FromString,
            response_serializer=Sample.SerializeToString,
        ),
        'getAllUrination': grpc.unary_stream_rpc_method_handler(
            servicer.getAllUrination,
            request_deserializer=PuckId.FromString,
            response_serializer=Sample.SerializeToString,
        ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
        'Tinkl', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


  class BetaTinklServicer(object):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This class was generated
    only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0."""
    def getUrination(self, request, context):
      context.code(beta_interfaces.StatusCode.UNIMPLEMENTED)
    def getAllUrination(self, request, context):
      context.code(beta_interfaces.StatusCode.UNIMPLEMENTED)


  class BetaTinklStub(object):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This class was generated
    only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0."""
    def getUrination(self, request, timeout, metadata=None, with_call=False, protocol_options=None):
      raise NotImplementedError()
    getUrination.future = None
    def getAllUrination(self, request, timeout, metadata=None, with_call=False, protocol_options=None):
      raise NotImplementedError()


  def beta_create_Tinkl_server(servicer, pool=None, pool_size=None, default_timeout=None, maximum_timeout=None):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This function was
    generated only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0"""
    request_deserializers = {
      ('Tinkl', 'getAllUrination'): PuckId.FromString,
      ('Tinkl', 'getUrination'): PuckId.FromString,
    }
    response_serializers = {
      ('Tinkl', 'getAllUrination'): Sample.SerializeToString,
      ('Tinkl', 'getUrination'): Sample.SerializeToString,
    }
    method_implementations = {
      ('Tinkl', 'getAllUrination'): face_utilities.unary_stream_inline(servicer.getAllUrination),
      ('Tinkl', 'getUrination'): face_utilities.unary_unary_inline(servicer.getUrination),
    }
    server_options = beta_implementations.server_options(request_deserializers=request_deserializers, response_serializers=response_serializers, thread_pool=pool, thread_pool_size=pool_size, default_timeout=default_timeout, maximum_timeout=maximum_timeout)
    return beta_implementations.server(method_implementations, options=server_options)


  def beta_create_Tinkl_stub(channel, host=None, metadata_transformer=None, pool=None, pool_size=None):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This function was
    generated only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0"""
    request_serializers = {
      ('Tinkl', 'getAllUrination'): PuckId.SerializeToString,
      ('Tinkl', 'getUrination'): PuckId.SerializeToString,
    }
    response_deserializers = {
      ('Tinkl', 'getAllUrination'): Sample.FromString,
      ('Tinkl', 'getUrination'): Sample.FromString,
    }
    cardinalities = {
      'getAllUrination': cardinality.Cardinality.UNARY_STREAM,
      'getUrination': cardinality.Cardinality.UNARY_UNARY,
    }
    stub_options = beta_implementations.stub_options(host=host, metadata_transformer=metadata_transformer, request_serializers=request_serializers, response_deserializers=response_deserializers, thread_pool=pool, thread_pool_size=pool_size)
    return beta_implementations.dynamic_stub(channel, 'Tinkl', cardinalities, options=stub_options)
except ImportError:
  pass
# @@protoc_insertion_point(module_scope)