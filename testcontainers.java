///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.neo4j.driver:neo4j-java-driver:5.26.1
//DEPS org.testcontainers:neo4j:1.20.3


import org.neo4j.driver.GraphDatabase;
import org.testcontainers.containers.Neo4jContainer;

import static java.lang.System.out;
import static org.neo4j.driver.AuthTokens.*;

public class testcontainers {

    public static void main(String... args) {

        var neo4jContainer = new Neo4jContainer<>("neo4j:5.24.2")
            .withAdminPassword("myPassword")
            .withPlugins("apoc");
        neo4jContainer.start();

        var driver = GraphDatabase.driver(
            neo4jContainer.getBoltUrl(),
            basic("neo4j", neo4jContainer.getAdminPassword())
        );
        var result = driver
            .executableQuery("RETURN 'hello from the container'")
            .execute();

        result.records().forEach(record -> {
            out.printf("%s%n",
                record.get(0));
        });

        driver.close();
        neo4jContainer.stop();
    }
}
