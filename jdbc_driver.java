/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.neo4j:neo4j-jdbc-full-bundle:6.0.1

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.System.out;

public class jdbc_driver {

    public static void main(String... args) throws SQLException {
        var uri = System.getenv().getOrDefault("NEO4J_URI", "neo4j://localhost:7687");
        var username = System.getenv().getOrDefault("NEO4J_USERNAME", "neo4j");
        var password = System.getenv().getOrDefault("NEO4J_PASSWORD", "verysecret");

        // Cypher JDBC
        try (var connection = DriverManager.getConnection("jdbc:" + uri, username, password);
             var statement = connection.prepareStatement("""
                 MATCH (p:Person)-[:ACTED_IN]->(m:Movie) WHERE p.name = $1
                 RETURN p.name as name, collect(m.title) as titles""")) {
            statement.setString(1, args[0]);
            var result = statement.executeQuery();
            while (result.next()) {
                out.printf("%s acted in %s%n", result.getString("name"), result.getObject("titles"));
            }
        }

        // SQL JDBC
        var properties = new Properties();
        properties.put("username", username);
        properties.put("password", password);
        // enable always conversion
        properties.put("enableSQLTranslation", true);
        // needed for SQL 2 Cypher to find a "table" for the relationships
        properties.put("s2c.tableToLabelMappings", "movie_actors:ACTED_IN");
        try (var connection = DriverManager.getConnection("jdbc:" + uri, properties);
             var statement = connection.prepareStatement(connection.nativeSQL("""
                 SELECT p.name as name, collect(m.title) as titles FROM Person p
                 JOIN movie_actors r on  r.person_id = p.id
                 JOIN Movie m on m.id = r.person_id
                 WHERE p.name = ?"""))) {
            statement.setString(1, args[0]);
            var result = statement.executeQuery();
            while (result.next()) {
                out.printf("%s acted in %s%n", result.getString("name"), result.getObject("titles"));
            }
        }
    }

}
