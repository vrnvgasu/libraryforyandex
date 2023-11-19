package ru.demo.libraryforyandex.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.demo.libraryforyandex.integration.annotation.IT;

@IT
@Slf4j
public class IntegrationTestBase {

	private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

	@BeforeAll
	static void runContainer() {
		container.start();
	}

	@DynamicPropertySource
	static void postgresProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		String url = container.getJdbcUrl();
		log.info(url);
	}

}
