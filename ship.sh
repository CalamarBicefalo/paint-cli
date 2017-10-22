#!/usr/bin/env bash

set -e -x

rm -rf .gradle build out
./gradlew build
rm -rf .gradle build out
echo 'Ready to go!'