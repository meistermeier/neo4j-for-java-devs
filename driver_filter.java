///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.neo4j.driver:neo4j-java-driver:5.26.1

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;

import java.util.Map;

import static java.lang.System.out;

public class driver_filter {

    public static void main(String... args) {
        var uri = System.getenv().getOrDefault("NEO4J_URI", "neo4j://localhost:7687");
        var username = System.getenv().getOrDefault("NEO4J_USERNAME", "neo4j");
        var password = System.getenv().getOrDefault("NEO4J_PASSWORD", "verysecret");
        var nameFilter = args[0];

        var driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        var result = driver.executableQuery("""
                MATCH (p:Person)-[:ACTED_IN]->(m:Movie) WHERE p.name = $name
                RETURN p.name as name, collect(m.title) as titles""")
            .withParameters(Map.of("name", nameFilter))
            .execute();

        result.records().forEach(record -> {
            out.printf("%s acted in %s%n",
                record.get("name"), record.get("titles"));
        });

    }
}
