package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidAddressException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("AddressTest 클래스")
class AddressTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 정상적인 1~255자 이하의 baseAddress 가 주어진다면")
        class Context_with_vaild_baseAddress {

            @ParameterizedTest
            @ValueSource(strings = {"1", "서울 강남구 강남대로62길 23 4층"})
            @DisplayName("Address 객체를 반환한다")
            void It_returns_a_object(String baseAddress) {
                Address address = new Address(baseAddress);

                assertThat(address.getBaseAddress()).isEqualTo(baseAddress);
            }
        }

        @Nested
        @DisplayName("만약 정상적인 1~255자 이하의 baseAddress 와 1~50자 이하의 detailAddress 가 주어진다면")
        class Context_with_vaild_baseAddress_detailAddress {

            @ParameterizedTest
            @CsvSource({
                    "서울 강남구 강남대로62길 23, 4층",
                    "서울 송파구 석촌호수로 262, 1층"
            })
            @DisplayName("Address 객체를 반환한다")
            void It_returns_a_object(String baseAddress, String detailAddress) {
                Address address = new Address(baseAddress, detailAddress);

                assertThat(address.getBaseAddress()).isEqualTo(baseAddress);
                assertThat(address.getDetailAddress()).isEqualTo(detailAddress);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 baseAddress 가 주어진다면")
        class Context_with_invaild_baseAddress {

            @ParameterizedTest
            @NullSource
            @MethodSource("provideAddressForIsNullAndEmptyAndOver255char")
            @DisplayName("예외를 발생시킨다")
            void It_throws_error(String baseAddress) {
                assertThatThrownBy(() -> new Address(baseAddress))
                        .isInstanceOf(InvalidAddressException.class);
            }

            private static Stream<Arguments> provideAddressForIsNullAndEmptyAndOver255char() {
                String content = "a";
                return Stream.of(
                        Arguments.of(""),
                        Arguments.of(" "),
                        Arguments.of("\t"),
                        Arguments.of("\n"),
                        Arguments.of(content.repeat(255 + 1))
                );
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 baseAddress 와 detailAddress 가 주어진다면")
        class Context_with_invaild_baseAddress_detailAddress {

            @ParameterizedTest
            @MethodSource("provideAddressForIsNullAndEmptyAndOver50char")
            @DisplayName("예외를 발생시킨다")
            void It_throws_error(String detailAddress) {
                String baseAddress = "서울 강남구 강남대로62길 23";

                assertThatThrownBy(() -> new Address(baseAddress, detailAddress))
                        .isInstanceOf(InvalidAddressException.class);
            }

            private static Stream<Arguments> provideAddressForIsNullAndEmptyAndOver50char() {
                String content = "a";
                return Stream.of(
                        Arguments.of(""),
                        Arguments.of(" "),
                        Arguments.of("\t"),
                        Arguments.of("\n"),
                        Arguments.of(content.repeat(50 + 1))
                );
            }
        }
    }
}