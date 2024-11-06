# Neo4j for Java Developers examples

A collection of [JBang](https://www.jbang.dev/) scripts that are the sources of my [Neo4j for Java Developers talk](https://speakerdeck.com/meistermeier/neo4j-for-java-developers).

All examples are pretty basic and require a running Neo4j instance with the movie example data set (`:play movies`) created.

## Driver basic example

```shell
jbang driver_basic.java
```

## Driver filter example

```shell
jbang driver_filter.java "<actor name>"
```

## Neo4j Cypher DSL example

```shell
jbang cypherdsl.java "<actor name>"
```

## Spring Data Neo4j example

```shell
jbang spring/spring_data_neo4j.java "<actor name>"
```

## Testcontainers Neo4j example

```shell
jbang testcontainers.java
```
