#!/bin/bash

FORMATTER_BIN=clang-format
FORMATTER_TARGET_DIR=src/

if [ -x "$(command -v $FORMATTER_BIN)" ]; then
  echo Running \`$FORMATTER_BIN\' in directory \`$FORMATTER_TARGET_DIR\'
  find $FORMATTER_TARGET_DIR -type f -exec $FORMATTER_BIN -style=file -i {} \;
else
  echo error: $FORMATTER_BIN not found. Please run manually or install it on your system. >&2
  exit 1
fi
