///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.neo4j.driver:neo4j-java-driver:5.26.1


import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;

import static java.lang.System.*;

public class driver_basic {

    public static void main(String... args) {
        var uri = System.getenv().getOrDefault("NEO4J_URI", "neo4j://localhost:7687");
        var username = System.getenv().getOrDefault("NEO4J_USERNAME", "neo4j");
        var password = System.getenv().getOrDefault("NEO4J_PASSWORD", "verysecret");

        var driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        var result = driver.executableQuery("""
                MATCH (p:Person)-[:ACTED_IN]->(m:Movie)
                RETURN p.name as name, collect(m.title) as titles""")
            .execute();

        result.records().forEach(record -> {
            out.printf("%s acted in %s%n",
                record.get("name"), record.get("titles"));
        });

    }
}
