package com.jjikmuk.sikdorak.unittest.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.store.domain.StoreName;
import com.jjikmuk.sikdorak.store.exception.InvalidStoreNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : StoreName 클래스")
class StoreNameTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 정상적인 1~50자 이하의 storeName 이 주어진다면")
        class Context_with_vaild_storeName {

            @ParameterizedTest
            @ValueSource(strings = {"1", "123456789_123456789_123456789_123456789_123456789_"})
            @DisplayName("StoreName 객체를 반환한다")
            void It_returns_a_object(String name) {
                StoreName storeName = new StoreName(name);

                assertThat(storeName.getStoreName()).isEqualTo(name);
            }
        }

        @Nested
        @DisplayName("만약 유효하지 않은 storeName 이 주어진다면")
        class Context_with_invaild_storeName {

            @ParameterizedTest
            @NullSource
            @ValueSource(strings = {"", "123456789_123456789_123456789_123456789_123456789_123456789_"})
            @DisplayName("예외를 발생시킨다")
            void It_returns_a_object(String name) {
                assertThatThrownBy(() -> new StoreName(name))
                        .isInstanceOf(InvalidStoreNameException.class);
            }
        }
    }
}
