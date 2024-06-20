package com.gamemoonchul;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemoonchul.application.S3Service;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import({DBInitializer.class})
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
@Transactional
// @ActiveProfiles("test")
public abstract class TestDataBase {

    private static final String BUCKET_NAME = "test";
    private static final String ROOT = "test";
    private static final String ROOT_PASSWORD = "1234";
    private static final String MYSQL_VERSION = "mysql:8.0.37";
    private static final String REDIS_IMAGE = "redis:7.0.8-alpine";
    private static final int REDIS_PORT = 6379;
    private static final GenericContainer REDIS_CONTAINER;
    private static final DockerImageName LOCALSTACK_NAME = DockerImageName.parse("localstack/localstack");
    @Container
    public static LocalStackContainer s3Container = new LocalStackContainer(LOCALSTACK_NAME).withServices(S3);
    @Container
    protected static MySQLContainer container;

    static {
        container = (MySQLContainer) new MySQLContainer(MYSQL_VERSION).withDatabaseName("test")
                .withUsername(ROOT)
                .withPassword(ROOT_PASSWORD);
        container.start();

        REDIS_CONTAINER = new GenericContainer(REDIS_IMAGE).withExposedPorts(REDIS_PORT)
                .withReuse(true);
        REDIS_CONTAINER.start();
    }

    @LocalServerPort
    protected int port;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private DBInitializer dbInitializer;

    @BeforeAll
    static void setup() {
        s3Container.start();
    }

    @AfterAll
    static void teardown() {
        s3Container.stop();
    }

    @DynamicPropertySource
    private static void configureProperties(final DynamicPropertyRegistry registry) {
        configureMySQLProperties(registry);
        configureRedisProperties(registry);
    }

    private static void configureMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", () -> ROOT);
        registry.add("spring.datasource.password", () -> ROOT_PASSWORD);
    }

    private static void configureRedisProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(REDIS_PORT)
                .toString());
    }

    @BeforeEach
    void delete() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }
        dbInitializer.clear();
    }

    public S3Service s3Service() {
        AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Container.getEndpointOverride(LocalStackContainer.Service.S3)
                        .toString(), s3Container.getRegion()))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test", "test")))
                .build();
        amazonS3Client.createBucket(BUCKET_NAME);

        return new S3Service(BUCKET_NAME, amazonS3Client);
    }

}