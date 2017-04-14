#!/usr/bin/env sh

PYTHON_DIR="../server/"

mkdir -p $PYTHON_DIR
protoc --python_out=$PYTHON_DIR tinkl.proto && \
    echo "Successfully compiled tinkl.proto"
