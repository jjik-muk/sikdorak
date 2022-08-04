package com.jjikmuk.sikdorak.acceptance;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;

import java.util.ArrayList;
import java.util.List;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.restdocs.snippet.Snippet;

public interface DocumentFormatGenerator {

	FieldDescriptor DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR =
		fieldWithPath("code")
			.type(JsonFieldType.STRING)
			.description("식도락 서버 전용 상태코드");

	FieldDescriptor DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("message")
			.type(JsonFieldType.STRING)
			.description("자세한 응답 메세지");

	static Snippet commonRequestFieldsWithValidConstraints(Class<?> clazz,
		FieldDescriptor... fields) {
		List<FieldDescriptor> commonRequestFields = new ArrayList<>();
		for (FieldDescriptor field : fields) {
			commonRequestFields.add(
				field.attributes(getFieldConstraints(clazz, field.getPath()))
			);
		}

		return requestFields(commonRequestFields);
	}

	static Snippet commonResponseFieldsWithValidConstraints(Class<?> clazz,
		FieldDescriptor... fields) {
		final String dataPrefix = "data.[].";
		List<FieldDescriptor> commonResponseFields = getCommonResponseFields();

		for (FieldDescriptor field : fields) {
			commonResponseFields.add(
				fieldWithPath(dataPrefix + field.getPath())
					.type(field.getType())
					.description(field.getDescription())
					.attributes(getFieldConstraints(clazz, field.getPath()))
			);
		}

		return responseFields(commonResponseFields);
	}

	private static List<FieldDescriptor> getCommonResponseFields() {
		List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
		fieldDescriptors.add(DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR);
		fieldDescriptors.add(DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS);
		return fieldDescriptors;
	}

	static Snippet commonResponseNonFields() {
		List<FieldDescriptor> commonResponseFields = getCommonResponseFields();

		commonResponseFields.add(
			fieldWithPath("data")
				.type(JsonFieldType.NULL)
				.description("데이터는 없습니다.")
		);

		return responseFields(commonResponseFields);
	}

	private static Attribute getFieldConstraints(Class<?> clazz, String path) {
		ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(clazz);
		return key("constraints").value(
			constraintDescriptions.descriptionsForProperty(path));
	}
}
