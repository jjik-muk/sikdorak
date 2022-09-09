package com.jjikmuk.sikdorak.tool.mock;

import com.jjikmuk.sikdorak.tool.extension.PlaceMocksExtention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
	"api.kakao.api_url=http://localhost:${wiremock.server.port}"
})
@ExtendWith(PlaceMocksExtention.class)
public @interface WireMockPlaceApiTest {

}
