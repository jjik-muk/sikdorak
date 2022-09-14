package com.jjikmuk.sikdorak.unittest.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.jjikmuk.sikdorak.store.controller.request.UserLocationInfoRequest;
import com.jjikmuk.sikdorak.store.exception.InvalidUserLocationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("단위 : UserLocationInfoRequest 클래스")
public class UserLocationInfoRequestTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 정상적인 위치정보가 주어진다면")
        class Context_with_valid_location_info {

            @Test
            @DisplayName("UserLocationInfoRequest 객체를 반환한다")
            void it_returns_a_object() {
                UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(180.0, 90.0, 20000);

                assertThat(userLocationInfoRequest.getX()).isEqualTo(180.0);
                assertThat(userLocationInfoRequest.getY()).isEqualTo(90.0);
                assertThat(userLocationInfoRequest.getRadius()).isEqualTo(20000);
            }
        }

        @Nested
        @DisplayName("만약 경도가 최대/최소범위를 넘어가는 위치정보가 주어진다면")
        class Context_with_invalid_longitude {

            @Test
            @DisplayName("InvalidUserLocationException을 반환한다")
            void it_returns_exception() {

                assertAll(() -> {
                    assertThatThrownBy(() -> new UserLocationInfoRequest(181.0, 90.0, 20000))
                        .isInstanceOf(InvalidUserLocationException.class);
                    assertThatThrownBy(() -> new UserLocationInfoRequest(-181.0, 90.0, 20000))
                        .isInstanceOf(InvalidUserLocationException.class);
                });
            }
        }

        @Nested
        @DisplayName("만약 위도가 최대/최소범위를 넘어가는 위치정보가 주어진다면")
        class Context_with_invalid_latitude {

            @Test
            @DisplayName("InvalidUserLocationException을 반환한다")
            void it_returns_exception() {

                assertAll(() -> {
                    assertThatThrownBy(() -> new UserLocationInfoRequest(180.0, 91.0, 20000))
                        .isInstanceOf(InvalidUserLocationException.class);
                    assertThatThrownBy(() -> new UserLocationInfoRequest(-180.0, -91.0, 20000))
                        .isInstanceOf(InvalidUserLocationException.class);
                });
            }
        }

        @Nested
        @DisplayName("만약 반경이 최대/최소범위를 넘어가는 위치정보가 주어진다면")
        class Context_with_invalid_radius {

            @Test
            @DisplayName("InvalidUserLocationException을 반환한다")
            void it_returns_exception() {

                assertAll(() -> {
                    assertThatThrownBy(() -> new UserLocationInfoRequest(180.0, 90.0, 99))
                        .isInstanceOf(InvalidUserLocationException.class);
                    assertThatThrownBy(() -> new UserLocationInfoRequest(-180.0, -90.0, 200001))
                        .isInstanceOf(InvalidUserLocationException.class);
                });
            }
        }

    }
}
