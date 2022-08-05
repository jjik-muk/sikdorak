package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidContactNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("ContactNumberTest 클래스")
class ContactNumberTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 정상적인 9~13자 이하의 contactNumber 가 주어진다면")
        class Context_with_vaild_storeName {


            @ParameterizedTest
            @ValueSource(strings = {"00-000-0000","00-0000-0000","000-000-0000","000-0000-0000"})
            @DisplayName("ContactNumber 객체를 반환한다")
            void It_returns_a_object(String number) {
                ContactNumber contactNumber = new ContactNumber(number);

                assertThat(contactNumber.getContactNumber()).isEqualTo(number);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 contactNumber 이 주어진다면")
        class Context_with_invaild_storeName {

            @ParameterizedTest
            @NullSource
            @ValueSource(strings = {"", "1234", "123456789_123456789_", "가나다라", "abc-abcd-abcd"})
            @DisplayName("예외를 발생시킨다")
            void It_returns_a_object(String number) {
                assertThatThrownBy(() -> new ContactNumber(number))
                        .isInstanceOf(InvalidContactNumberException.class);
            }
        }
    }
}
