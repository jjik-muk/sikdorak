package com.jjikmuk.sikdorak.integration;

import com.jjikmuk.sikdorak.common.DatabaseConfigurator;
import com.jjikmuk.sikdorak.common.MysqlTestContainer;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public abstract class InitIntegrationTest extends MysqlTestContainer {

	@Autowired
	private DatabaseConfigurator databaseConfigurator;

	@AfterEach
	void tearDown() {
		databaseConfigurator.clear();
	}
}
