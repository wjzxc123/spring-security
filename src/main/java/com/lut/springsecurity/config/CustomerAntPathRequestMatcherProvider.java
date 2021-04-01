package com.lut.springsecurity.config;


import java.util.function.Function;

import org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/1 10:55
 */
public class CustomerAntPathRequestMatcherProvider implements RequestMatcherProvider {

	private final Function<String,AntPath> factory;

	public CustomerAntPathRequestMatcherProvider(Function<String, AntPath> factory) {
		this.factory = factory;
	}

	@Override
	public RequestMatcher getRequestMatcher(String pattern) {
		return new AntPathRequestMatcher(this.factory.apply(pattern).getPath(),this.factory.apply(pattern).getMethod());
	}
}
