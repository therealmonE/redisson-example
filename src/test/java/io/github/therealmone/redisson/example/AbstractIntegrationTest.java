package io.github.therealmone.redisson.example;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = {AbstractIntegrationTest.Initializer.class}
)
public abstract class AbstractIntegrationTest {

    private static final int MONGO_PORT = 27017;
    private static final int REDIS_PORT = 6379;

    private static final GenericContainer MONGO = new GenericContainer("mongo:4.0")
            .withExposedPorts(MONGO_PORT);

    private static final GenericContainer REDIS = new GenericContainer("redis:alpine")
            .withExposedPorts(REDIS_PORT);

    static {
        MONGO.start();
        REDIS.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.data.mongodb.port=" + MONGO.getMappedPort(MONGO_PORT),
                    "redis.address=redis://localhost:" + REDIS.getMappedPort(REDIS_PORT)
            ).applyTo(applicationContext);
        }

    }
}
