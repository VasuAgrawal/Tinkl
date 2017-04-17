// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tinkl.proto

package com.tinkl.tinkl;

public interface SampleOrBuilder extends
    // @@protoc_insertion_point(interface_extends:routeguide.Sample)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <pre>
   * Unique within urination, monotonic
   * </pre>
   *
   * <code>optional int32 sample_id = 1;</code>
   */
  int getSampleId();

  /**
   * <pre>
   * Temperature reading in C
   * </pre>
   *
   * <code>optional float temperature = 2;</code>
   */
  float getTemperature();

  /**
   * <code>optional int32 turbidity = 3;</code>
   */
  int getTurbidity();

  /**
   * <code>optional .routeguide.Color color = 4;</code>
   */
  boolean hasColor();
  /**
   * <code>optional .routeguide.Color color = 4;</code>
   */
  com.tinkl.tinkl.Color getColor();
}
