package dk.roninit.kafka.mongo.example;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataMongoTest
public class MyDataMongoTest {

    @Test
    void verifyTestDbName(@Autowired final MongoTemplate mongoTemplate) {
        System.out.println(mongoTemplate.getDb().getName());
        Assertions.assertThat(mongoTemplate.getDb().getName()).isEqualTo("springboot-mongo");
    }

    @Test
    void saveObjectToEmbbedMong(@Autowired final MongoTemplate mongoTemplate) {
        System.out.println("MongoTemplate: " + mongoTemplate.getDb().getName());
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
            .add("key", "value")
            .get();

        // when
        mongoTemplate.save(objectToSave, "myCollection");

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "myCollection")).extracting("key")
            .containsOnly("value");
    }
}
