package com.jjikmuk.sikdorak.acceptance;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Attributes.Attribute;

public interface DocumentFormatGenerator {

	static FieldDescriptor fieldWithPathAndValidConstraints(String path, Class<?> clazz) {
		FieldDescriptor fieldWithPath = fieldWithPath(path);

		ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(clazz);
		Attribute constraints = key("constraints").value(
			constraintDescriptions.descriptionsForProperty(path));

		fieldWithPath.attributes(constraints);

		return fieldWithPath;
	}

}
