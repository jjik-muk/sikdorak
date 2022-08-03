package com.jjikmuk.sikdorak.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfigurator implements InitializingBean {

	@PersistenceContext
	private EntityManager entityManager;

	private List<String> tableNames;

	@Override
	public void afterPropertiesSet() {
		entityManager.unwrap(Session.class).doWork(this::extractTableNames);
	}

	// reference : https://www.baeldung.com/jdbc-database-metadata
	private void extractTableNames(Connection connection) throws SQLException {
		List<String> tableNames = new ArrayList<>();

		ResultSet tables = connection
			.getMetaData()
			.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});

		try (tables) {
			while (tables.next()) {
				tableNames.add(tables.getString("table_name"));
			}

			this.tableNames = tableNames;
		}
	}

	public void clear() {
		entityManager.unwrap(Session.class).doWork(this::cleanUpDatabase);
	}

	private void cleanUpDatabase(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0"); // MySQL 참조무결성 off

			for (String tableName : tableNames) {

				statement.executeUpdate("TRUNCATE TABLE " + tableName);
				// Mysql 8에서 auto_increment 초기화 지원 X
//				statement
//					.executeUpdate("ALTER TABLE " + tableName + " ALTER AUTO_INCREMENT = 1");
			}

			statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
		}
	}

}
