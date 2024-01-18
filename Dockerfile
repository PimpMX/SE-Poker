# Build Stage
FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1 AS build
WORKDIR /se-poker
COPY . /se-poker
RUN sbt compile

# Run Stage
# FROM openjdk:8-jre-alpine
# WORKDIR /se-poker
# COPY . /se-poker

CMD sbt run
