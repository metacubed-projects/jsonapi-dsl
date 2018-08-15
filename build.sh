#!/usr/bin/env sh -e -o pipefail

gradle wrapper && ./gradlew clean build
