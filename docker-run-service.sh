#!/bin/bash

./mvnw clean package -DskipTests
POM_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)

docker build --build-arg JAR_PATH=target/product-service-"$POM_VERSION"-jar-with-dependencies.jar \
  -t nshigarov/product-service:"$POM_VERSION" \
  -t nshigarov/product-service:latest \
  .

docker-compose down -v

docker-compose up

