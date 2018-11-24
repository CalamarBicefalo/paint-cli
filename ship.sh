#!/usr/bin/env bash

set -e

git pull -r

rm -rf .gradle build out
./gradlew build
rm -rf .gradle build out
echo 'Ready to go!'

git push