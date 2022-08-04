package com.jjikmuk.sikdorak.acceptance;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes.Attribute;

public interface DocumentFormatGenerator {

	FieldDescriptor DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR =
		fieldWithPath("code")
			.type(JsonFieldType.STRING)
			.description("식도락 서버 전용 상태코드");

	FieldDescriptor DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("message")
			.type(JsonFieldType.STRING)
			.description("자세한 응답 메세지");

	static FieldDescriptor fieldWithPathAndValidConstraints(String path, Class<?> clazz) {
		FieldDescriptor fieldWithPath = fieldWithPath(path);

		ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(clazz);
		Attribute constraints = key("constraints").value(
			constraintDescriptions.descriptionsForProperty(path));

		fieldWithPath.attributes(constraints);

		return fieldWithPath;
	}

}
