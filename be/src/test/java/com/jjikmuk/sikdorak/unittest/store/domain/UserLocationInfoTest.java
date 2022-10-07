package com.jjikmuk.sikdorak.unittest.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.store.command.domain.UserLocationInfo;
import com.jjikmuk.sikdorak.store.exception.InvalidUserLocationException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("단위 : UserLocationInfo 클래스")
public class UserLocationInfoTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 정상적인 위치정보가 주어진다면")
        class Context_with_valid_location_info {

            @Test
            @DisplayName("UserLocationInfo 객체를 반환한다")
            void it_returns_a_object() {
                UserLocationInfo userLocationInfo = new UserLocationInfo(180.0, 90.0, 20000);

                assertThat(userLocationInfo.x()).isEqualTo(180.0);
                assertThat(userLocationInfo.y()).isEqualTo(90.0);
                assertThat(userLocationInfo.radius()).isEqualTo(20000);
            }
        }

        @Nested
        @DisplayName("만약 경도가 최대/최소범위를 넘어가는 위치정보가 주어진다면")
        class Context_with_invalid_longitude {

            @ParameterizedTest
            @MethodSource("provide_invalid_location_info")
            @DisplayName("InvalidUserLocationException을 반환한다")
            void it_returns_exception(double x, double y, int radius) {

                assertThatThrownBy(() -> new UserLocationInfo(x, y, radius))
                    .isInstanceOf(InvalidUserLocationException.class);
            }

            private static Stream<Arguments> provide_invalid_location_info() {
                return Stream.of(
                    Arguments.of(181.0, 90.0, 20000),
                    Arguments.of(-181.0, 90.0, 20000),
                    Arguments.of(180.0, 91.0, 20000),
                    Arguments.of(180.0, -91.0, 20000),
                    Arguments.of(181.0, 90.0, 99),
                    Arguments.of(181.0, 90.0, 20001)
                );
            }
        }

    }
}
