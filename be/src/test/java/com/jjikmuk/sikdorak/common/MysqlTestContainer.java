package com.jjikmuk.sikdorak.common;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class MysqlTestContainer {

	private static final String MYSQL_VERSION = "mysql:8";
	private static final String DATABASE_NAME = "testDB";

	static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer(MYSQL_VERSION)
		.withDatabaseName(DATABASE_NAME);

	@BeforeAll
	static void beforeAll() {
		MYSQL_CONTAINER.start();
	}
}
