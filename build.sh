#!/usr/bin/env sh -e -o pipefail

mvn -s .travis.settings.xml -N io.takari:maven:wrapper -Dmaven=3.6.0 && echo && ./mvnw -s .travis.settings.xml clean install
