# Prototype Akka Persistence Serializer

[![shields.io](http://img.shields.io/badge/license-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)](#)

**Prototypes an akka persistence project with a custom json serializer (written in scala)**

Author: Maximilian Bundscherer (https://bundscherer-online.de)

Test-Coverage: **86.99%**

## Description

The project is written in **scala**. Akka persistence toolkit and custom serializer based on circe (json library for scala) have been used.

SBT (and for default journal and snapshot storage: redis-server) required

- See config located in ``./src/main/resources/application.conf`` (opt. change redis config)
- Test project with ``sbt clean coverage test``
- Generate coverage report(s) with ``sbt coverageReport``

### Used technologies

- Akka Persistence: *Event sourcing*
- Scala: *programming language*
- ScalaTest: *testing project*
- Circe: *json library for scala*
- sbt-scoverage: *generate test coverage report(s)*

### Journal and snapshot-storage

There are many possibilities to run journal and snapshot-storage. In this project there are included:

- redis snapshot- and event-storage (**default**)
- local file-system snapshot-storage and in memory event storage

You can extend this project with any other journal and snapshot-storage extension(s)

## Description / Features

- See the tests in order to understand the project
