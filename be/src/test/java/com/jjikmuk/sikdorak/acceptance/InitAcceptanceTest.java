package com.jjikmuk.sikdorak.acceptance;

import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InitAcceptanceTest {

	@Autowired
	StoreRepository storeRepository;

	@LocalServerPort
	int port;

	Store savedStore;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		savedStore = storeRepository.save(new Store());
	}

	@AfterEach
	void tearDown() {
		storeRepository.deleteAll();
	}
}
