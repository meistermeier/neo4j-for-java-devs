# Neo4j for Java Developers examples

A collection of [JBang](https://www.jbang.dev/) scripts that are the sources of my [Neo4j for Java Developers talk](https://speakerdeck.com/meistermeier/neo4j-for-java-developers).

All examples are pretty basic and require a running Neo4j instance with the movie example data set (`:play movies`) created.

The first three examples accept environment variables for connectivity.
```
NEO4J_URI     
NEO4J_USERNAME
NEO4J_PASSWORD
```

## Driver basic example

[GitHub: https://github.com/neo4j/neo4j-java-driver/](https://github.com/neo4j/neo4j-java-driver/)

[Documentation: https://neo4j.com/docs/java-manual/current/](https://neo4j.com/docs/java-manual/current/)

```shell
jbang driver_basic.java
```

## Driver filter example

```shell
jbang driver_filter.java "<actor name>"
```

## Neo4j Cypher DSL example

[GitHub: https://github.com/neo4j/cypher-dsl](https://github.com/neo4j/cypher-dsl)

[Documentation: https://neo4j.github.io/cypher-dsl/](https://neo4j.github.io/cypher-dsl/)

```shell
jbang cypherdsl.java "<actor name>"
```

## Neo4j JDBC example

[GitHub: https://github.com/neo4j/neo4j-jdbc](https://github.com/neo4j/neo4j-jdbc)

[Documentation: http://neo4j.github.io/neo4j-jdbc/](http://neo4j.github.io/neo4j-jdbc/)

```shell
jbang jdbc_driver.java "<actor name>"
```

## Spring Data Neo4j example

[GitHub: https://github.com/spring-projects/spring-data-neo4j](https://github.com/spring-projects/spring-data-neo4j)

[Documentation: https://docs.spring.io/spring-data/neo4j/reference/#reference](https://docs.spring.io/spring-data/neo4j/reference/#reference)

To change the connection parameters, you could either edit the _application.properties_ 
or pass in the environment variables

```
SPRING_NEO4J_URI
SPRING_NEO4J_AUTHENTICATION_USERNAME
SPRING_NEO4J_AUTHENTICATION_PASSWORD
```

```shell
jbang spring/spring_data_neo4j.java "<actor name>"
```

## Testcontainers Neo4j example

```shell
jbang testcontainers.java
```
