package com.jjikmuk.sikdorak.acceptance;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjikmuk.sikdorak.common.DatabaseConfigurator;
import com.jjikmuk.sikdorak.common.MysqlTestContainer;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.mapping.Jackson2Mapper;
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
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public class InitAcceptanceTest extends MysqlTestContainer {

	protected static final String DEFAULT_RESTDOC_PATH = "{class_name}/{method_name}/";
	protected static final String DEFAULT_RESTDOC_SCHEMA = "https";
	protected static final String DEFAULT_RESTDOC_HOST = "sikdorak.herokuapp.com";

	public static final FieldDescriptor DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR =
		fieldWithPath("code")
		.type(JsonFieldType.STRING)
		.description("식도락 서버 전용 상태코드");

	public static final FieldDescriptor DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("message")
		.type(JsonFieldType.STRING)
		.description("자세한 응답 메세지");

	protected RequestSpecification spec;

	@Autowired
	DatabaseConfigurator databaseConfigurator;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	protected ObjectMapper objectMapper;

	@LocalServerPort
	int port;

	public Store savedStore;

	{
		setUpRestAssured();
	}

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		savedStore = storeRepository.save(new Store());
	}

	@BeforeEach
	void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
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

	@AfterEach
	void tearDown() {
		databaseConfigurator.clear();
	}

	private void setUpRestAssured() {
		RestAssured.config = RestAssuredConfig.config()
			.objectMapperConfig(
				new ObjectMapperConfig(
					new Jackson2Mapper((type, charset) -> objectMapper)));
	}
}
