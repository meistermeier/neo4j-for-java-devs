///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.springframework.boot:spring-boot-starter-data-neo4j:3.3.5
//FILES application.properties

package spring;
import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.List;

import static java.lang.System.out;

@SpringBootApplication
@EnableNeo4jRepositories(considerNestedRepositories = true)
public class spring_data_neo4j {

    interface PersonRepository extends Neo4jRepository<Person, String> {
        Person findByName(String name);

    }
    record Person(@Id String name, @Relationship("ACTED_IN") List<Movie> movies) {}

    record Movie(@Id String title){}

    @Bean
    public CommandLineRunner commandLineRunner(PersonRepository repository) {
        return args -> out.println(repository.findByName(args[0]));
    }

    @Bean
    public Configuration cypherDslConfiguration() {
        return Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
    }

    public static void main(String... args) {
        SpringApplication.run(spring_data_neo4j.class, args);
    }
}
