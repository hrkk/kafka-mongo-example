package dk.roninit.kafka.mongo.example;

import dk.roninit.kafka.mongo.example.model.Customer;
import dk.roninit.kafka.mongo.example.mongo.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;
    @BeforeEach
    void setup() {
        repository.deleteAll();
        repository.deleteAll();

        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));
    }

    @Test
    void repositoryTest(@Autowired final MongoTemplate mongoTemplate) {
        System.out.println(mongoTemplate.getDb().getName());
        Assertions.assertThat(mongoTemplate.getDb().getName()).isEqualTo("springboot-mongo");

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }
}
