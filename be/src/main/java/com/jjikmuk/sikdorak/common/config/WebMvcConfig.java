package com.jjikmuk.sikdorak.common.config;

import com.jjikmuk.sikdorak.user.auth.controller.OAuthUserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${webconfig.cors.allowedOrigins}")
	private String[] allowedOrigins;

	private final OAuthUserArgumentResolver oAuthUserArgumentResolver;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowCredentials(true)
			.allowedOrigins(allowedOrigins)
			.allowedMethods(
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name(),
				HttpMethod.OPTIONS.name()
			);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(oAuthUserArgumentResolver);
	}
}
