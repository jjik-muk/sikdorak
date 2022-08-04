package com.jjikmuk.sikdorak.acceptance;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjikmuk.sikdorak.common.DatabaseConfigurator;
import com.jjikmuk.sikdorak.common.MysqlTestContainer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public class InitAcceptanceTest extends MysqlTestContainer {

	protected static final String DEFAULT_RESTDOC_PATH = "{class_name}/{method_name}/";
	protected static final String DEFAULT_RESTDOC_SCHEMA = "https";
	protected static final String DEFAULT_RESTDOC_HOST = "sikdorak.herokuapp.com";

	protected RequestSpecification spec;

	@Autowired
	protected ObjectMapper objectMapper;

	@LocalServerPort
	protected int port;

	@Autowired
	protected DatabaseConfigurator data;

	{
		setUpRestAssured();
	}

	@BeforeEach
	void setUpDataBase() {
		data.clear();
		data.initDataSource();
	}

	@BeforeEach
	void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
		RestAssured.port = port;
		OperationPreprocessor requestOperationProcessor = modifyUris().scheme(
				DEFAULT_RESTDOC_SCHEMA)
			.host(DEFAULT_RESTDOC_HOST)
			.removePort();

		this.spec = new RequestSpecBuilder()
			.setPort(port)
			.addFilter(documentationConfiguration(restDocumentation)
				.operationPreprocessors()
				.withRequestDefaults(requestOperationProcessor, prettyPrint())
				.withResponseDefaults(prettyPrint()))
			.build();
	}

	private void setUpRestAssured() {
		RestAssured.config = RestAssuredConfig.config()
			.objectMapperConfig(
				new ObjectMapperConfig(
					new Jackson2Mapper((type, charset) -> objectMapper)));
	}
}
