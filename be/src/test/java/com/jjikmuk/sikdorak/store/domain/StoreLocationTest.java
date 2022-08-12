package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidStoreLocationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("StoreLocation 클래스")
class StoreLocationTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 정상적인 latitude, longitude 가 들어온다면")
        class Context_with_valid_storePoint {

            @ParameterizedTest
            @CsvSource({
                    "37.4908786, 127.033406",
                    "37.5093890, 127.105143",
            })
            @DisplayName("StorePoint 객체를 반환한다")
            void It_returns_a_object(double latitude, double longitude) {
                StoreLocation storePoint = new StoreLocation(latitude, longitude);

                assertThat(storePoint.latitude()).isEqualTo(latitude);
                assertThat(storePoint.longitude()).isEqualTo(longitude);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 latitude, longitude 가 들어온다면")
        class Context_with_invalid_storePoint {

            @ParameterizedTest
            @MethodSource("providePointNullOrOutOfRange")
            @DisplayName("예외를 발생시킨다")
            void It_throws_error(Double latitude, Double longitude) {
                assertThatThrownBy(() -> new StoreLocation(latitude, longitude))
                        .isInstanceOf(InvalidStoreLocationException.class);
            }

            public static Stream<Arguments> providePointNullOrOutOfRange() {
                return Stream.of(
                        Arguments.of(null, null),
                        Arguments.of(30.0, null),
                        Arguments.of(null, 100.0),
                        Arguments.of(-91.0, -181.0),
                        Arguments.of(-91.0, 127.033406),
                        Arguments.of(91.0, 181.0),
                        Arguments.of(91.0, 127.033406),
                        Arguments.of(37.5093890, -181.0),
                        Arguments.of(91.0, 181.0),
                        Arguments.of(37.5093890, 181.0)
                );
            }
        }
    }
}