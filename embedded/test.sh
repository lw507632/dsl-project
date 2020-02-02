#!/usr/bin/env bash

./build.sh

# java -jar ./target/dsl-1.0-jar-with-dependencies.jar scripts/helloworld.groovy
echo 'executing scenario ' $1
java -jar ./target/dsl-1.0-jar-with-dependencies.jar $1
