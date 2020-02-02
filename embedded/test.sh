#!/usr/bin/env bash

./build.sh

# java -jar ./target/dsl-1.0-jar-with-dependencies.jar scripts/helloworld.groovy
java -jar ./target/dsl-1.0-jar-with-dependencies.jar scripts/multiStateAlarmScenario.groovy
