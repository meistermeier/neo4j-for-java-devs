///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.neo4j.driver:neo4j-java-driver:5.26.1
//DEPS org.neo4j:neo4j-cypher-dsl:2024.2.0

import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;

import java.util.Map;

import static java.lang.System.out;
import static org.neo4j.cypherdsl.core.Cypher.*;

public class cypherdsl {

    public static void main(String... args) {
        var uri = System.getenv().getOrDefault("NEO4J_URI", "neo4j://localhost:7687");
        var username = System.getenv().getOrDefault("NEO4J_USERNAME", "neo4j");
        var password = System.getenv().getOrDefault("NEO4J_PASSWORD", "verysecret");
        var nameFilter = args[0];

        var personNode = node("Person").named("p");
        var movieNode = node("Movie").named("m");
        var relationship = personNode.relationshipTo(movieNode, "ACTED_IN");
        var match = match(relationship)
            .where(personNode.property("name").eq(parameter("name")));
        var statement = match.returning(
            personNode.property("name").as("name"),
            Cypher.collectDistinct(movieNode.property("title")).as("titles")
        ).build();

        var driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        var result = driver.executableQuery(statement.getCypher())
            .withParameters(Map.of("name", nameFilter))
            .execute();

        result.records().forEach(record -> {
            out.printf("%s acted in %s%n",
                record.get("name"), record.get("titles"));
        });

    }
}
