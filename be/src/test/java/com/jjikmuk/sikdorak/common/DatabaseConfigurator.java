package com.jjikmuk.sikdorak.common;

import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfigurator implements InitializingBean {

	@PersistenceContext
	private EntityManager entityManager;
	private List<String> tableNames;

	@Autowired
	private StoreRepository storeRepository;

	public Store store;

	public void initDataSource() {
		this.store = storeRepository.save(new Store("맛있는가게",
				"02-0000-0000",
				"서울시 송파구 좋은길 1",
				"1층 101호",
				37.5093890,
				127.105143));
	}

	public void clear() {
		entityManager.unwrap(Session.class).doWork(this::cleanUpDatabase);
	}

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

	private void cleanUpDatabase(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

			for (String tableName : tableNames) {
				statement.executeUpdate("TRUNCATE TABLE " + tableName);
			}

			statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
		}
	}

}
