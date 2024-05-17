package com.gamemoonchul;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(DBInitializer.class)
@TestPropertySource(locations = "classpath:application.yaml")
@AutoConfigureMockMvc
@Transactional
// @ActiveProfiles("test")
public abstract class TestDataBase {

    private static final String ROOT = "test";
    private static final String ROOT_PASSWORD = "1234";
    private static final String MYSQL_VERSION = "mysql:8.0.37";

    @LocalServerPort
    protected int port;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private DBInitializer dataInitializer;

    @Container
    protected static MySQLContainer container;

    @DynamicPropertySource
    private static void configureProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", () -> ROOT);
        registry.add("spring.datasource.password", () -> ROOT_PASSWORD);
    }

    static {
        container = (MySQLContainer) new MySQLContainer(MYSQL_VERSION)
            .withDatabaseName("test")
            .withUsername(ROOT)
            .withPassword(ROOT_PASSWORD);
        container.start();
    }

    @BeforeEach
    void delete() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }
        dataInitializer.clear();
    }
}