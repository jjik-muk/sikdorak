package com.jjikmuk.sikdorak.acceptance;

import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public class InitAcceptanceTest {

	protected static final String DEFAULT_RESTDOC_PATH = "{class_name}/{method_name}/";

	protected RequestSpecification spec;

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

	@BeforeEach
	void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
		this.spec = new RequestSpecBuilder()
				.addFilter(documentationConfiguration(restDocumentation)
						.operationPreprocessors()
						.withRequestDefaults(prettyPrint())
						.withResponseDefaults(prettyPrint()))
				.build();
	}

	@AfterEach
	void tearDown() {
		storeRepository.deleteAll();
	}
}
