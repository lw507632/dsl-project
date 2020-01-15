#!/usr/bin/env bash

# install the kernel
echo "INSTALLING THE KERNEL"
cd ./kernels/jvm
mvn clean install
cd ../..


