#!/usr/bin/env sh -e -o pipefail

gradle --console=verbose wrapper && echo && ./gradlew --console=verbose clean build
