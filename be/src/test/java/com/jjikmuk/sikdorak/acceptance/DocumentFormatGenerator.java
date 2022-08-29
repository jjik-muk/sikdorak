package com.jjikmuk.sikdorak.acceptance;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.restdocs.snippet.Snippet;

public interface DocumentFormatGenerator {

	ParameterDescriptor DEFAULT_PAGING_BEFORE_REQUEST_PARAMETER =
		parameterWithName("before")
			.description("이전 페이지를 가리키는 커서")
			.optional();

	ParameterDescriptor DEFAULT_PAGING_AFTER_REQUEST_PARAMETER =
		parameterWithName("after")
			.description("다음 페이지를 가리키는 커서")
			.optional();

	ParameterDescriptor DEFAULT_PAGING_SIZE_REQUEST_PARAMETER =
		parameterWithName("size")
			.description("페이지 크기 입니다.")
			.optional();

	FieldDescriptor DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR =
		fieldWithPath("code")
			.type(JsonFieldType.STRING)
			.description("식도락 서버 전용 상태코드");

	FieldDescriptor DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("message")
			.type(JsonFieldType.STRING)
			.description("자세한 응답 메세지");

	FieldDescriptor DEFAULT_NON_DATA_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("data")
			.type(JsonFieldType.NULL)
			.description("데이터는 없습니다.");

	FieldDescriptor DEFAULT_PAGING_SIZE_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("page.size")
			.type(JsonFieldType.NUMBER)
			.description("페이지 크기");

	FieldDescriptor DEFAULT_PAGING_PREV_CURSOR_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("page.prev")
			.type(JsonFieldType.NUMBER)
			.description("이전 페이지를 가리키는 커서");

	FieldDescriptor DEFAULT_PAGING_NEXT_CURSOR_RESPONSE_FIELD_DESCRIPTORS =
		fieldWithPath("page.next")
			.type(JsonFieldType.NUMBER)
			.description("다음 페이지를 가리키는 커서");

	/**
	 * 요청 객체의 필드 설명과 제약조건을 반환한다.
	 * @param clazz
	 * @param fields
	 * @return
	 */
	static Snippet requestSnippetWithConstraintsAndFields(Class<?> clazz,
		FieldDescriptor... fields) {
		List<FieldDescriptor> commonRequestFields = new ArrayList<>();
		for (FieldDescriptor field : fields) {
			commonRequestFields.add(
				field.attributes(getFieldConstraints(clazz, field.getPath()))
			);
		}

		return requestFields(commonRequestFields);
	}

	/**
	 * 요청 파라미터에 포함될 페이징 요청 파라미터들의 설명을 반환한다.
	 * @return List<FieldDescriptor>
	 */
	static Snippet requestPagingFieldsOfCommon() {
		return requestParameters(
			DEFAULT_PAGING_SIZE_REQUEST_PARAMETER,
			DEFAULT_PAGING_BEFORE_REQUEST_PARAMETER,
			DEFAULT_PAGING_AFTER_REQUEST_PARAMETER
		);
	}

	/**
	 * 응답 필드 설명 리스트로 Snippet 을 생성한다.<br>
	 * 참고 : https://icarus8050.tistory.com/88
	 * @param fieldsList
	 * @return Snippet
	 */
	@SafeVarargs
	static Snippet createResponseSnippetWithFields(List<FieldDescriptor>... fieldsList) {
		List<FieldDescriptor> mergedFields = new ArrayList<>();

		for (List<FieldDescriptor> fields : fieldsList) {
			mergedFields.addAll(fields);
		}

		return responseFields(mergedFields);
	}

	/**
	 * 공통 응답에 반환 데이터가 있는 경우의 필드 설명을 반환한다.
	 * @return List<FieldDescriptor>
	 */
	static List<FieldDescriptor> responseFieldsOfCommon() {
		List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
		fieldDescriptors.add(DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR);
		fieldDescriptors.add(DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS);
		return fieldDescriptors;
	}

	/**
	 * 공통 응답에 반환 데이터가 없는 경우의 필드 설명을 반환한다.
	 * @return List<FieldDescriptor>
	 */
	static List<FieldDescriptor> responseFieldsOfCommonNonData() {
		List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
		fieldDescriptors.add(DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR);
		fieldDescriptors.add(DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS);
		fieldDescriptors.add(DEFAULT_NON_DATA_RESPONSE_FIELD_DESCRIPTORS);
		return fieldDescriptors;
	}

	/**
	 * 공통 응답 반환 데이터에 포함될 페이징 응답 객체의 필드 설명을 반환한다.
	 * @return List<FieldDescriptor>
	 */
	static List<FieldDescriptor> responsePagingFieldsOfCommon() {
		return responseFieldsOfObjectWithConstraintsAndFields(
			CursorPageResponse.class,
			DEFAULT_PAGING_SIZE_RESPONSE_FIELD_DESCRIPTORS,
			DEFAULT_PAGING_PREV_CURSOR_RESPONSE_FIELD_DESCRIPTORS,
			DEFAULT_PAGING_NEXT_CURSOR_RESPONSE_FIELD_DESCRIPTORS
		);
	}

	/**
	 * 공통 응답 반환 데이터에 포함될 객체의 필드 설명과 제약조건을 반환한다.
	 * @param clazz
	 * @param fields
	 * @return List<FieldDescriptor>
	 */
	static List<FieldDescriptor> responseFieldsOfObjectWithConstraintsAndFields(Class<?> clazz,
		FieldDescriptor... fields) {
		final String dataPrefix = "data.";
		List<FieldDescriptor> responseFields = new ArrayList<>();

		for (FieldDescriptor field : fields) {
			if (field.getPath().contains(".")) { // sub class
				responseFields.add(
					fieldWithPath(dataPrefix + field.getPath())
						.type(field.getType())
						.description(field.getDescription())
						.attributes(getFieldConstraints(clazz, field.getPath().split("\\.")[1])));
			} else {
				responseFields.add(
					fieldWithPath(dataPrefix + field.getPath())
						.type(field.getType())
						.description(field.getDescription())
						.attributes(getFieldConstraints(clazz, field.getPath())));
			}
		}
		return responseFields;
	}

	/**
	 * 공통 응답 반환 데이터에 포함될 리스트의 필드 설명과 제약조건을 반환한다.
	 * @param clazz
	 * @param fields
	 * @return List<FieldDescriptor>
	 */
	static List<FieldDescriptor> responseFieldsOfListWithConstraintsAndFields(Class<?> clazz,
		FieldDescriptor... fields) {
		final String dataPrefix = "data.[].";
		List<FieldDescriptor> responseFields = new ArrayList<>();

		for (FieldDescriptor field : fields) {
			responseFields.add(
				fieldWithPath(dataPrefix + field.getPath())
					.type(field.getType())
					.description(field.getDescription())
					.attributes(getFieldConstraints(clazz, field.getPath())));
		}
		return responseFields;
	}

	@Deprecated
	static Snippet responseSnippetOfCommonWithConstraints(Class<?> clazz,
		FieldDescriptor... fields) {
		return createResponseSnippetWithFields(
			responseFieldsOfCommon(),
			responseFieldsOfObjectWithConstraintsAndFields(clazz, fields));
	}

	@Deprecated
	static Snippet responseSnippetOfCommonAndListWithConstraints(Class<?> clazz,
		FieldDescriptor... fields) {
		return createResponseSnippetWithFields(
			responseFieldsOfCommon(),
			responseFieldsOfListWithConstraintsAndFields(clazz, fields));
	}

	@Deprecated
	static Snippet responseSnippetOfCommonNonData() {
		return createResponseSnippetWithFields(responseFieldsOfCommonNonData());
	}

	private static Attribute getFieldConstraints(Class<?> clazz, String path) {
		ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(clazz);
		return key("constraints").value(
			constraintDescriptions.descriptionsForProperty(path));
	}
}

