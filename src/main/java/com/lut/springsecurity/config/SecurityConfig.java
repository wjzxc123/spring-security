package com.lut.springsecurity.config;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.security.servlet.AntPathRequestMatcherProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 13:49
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorise->authorise.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O object) {
				object.setAccessDecisionManager(new AffirmativeBased(Collections.singletonList(new RoleVoter())));
				object.setSecurityMetadataSource(new FilterInvocationSecurityMetadataSource() {
					@Override
					public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
						HttpServletRequest request = ((FilterInvocation) object).getRequest();


						RequestMatcher requestMatcher = new CustomerAntPathRequestMatcherProvider(path ->{
							if (path.contains(HttpMethod.GET.name())){
								return new AntPath(path,HttpMethod.GET.name());
							}else if (path.contains(HttpMethod.POST.name())){
								return new AntPath(path,HttpMethod.POST.name());
							}else {
								return new AntPath(path,HttpMethod.GET.name());
							}

						}).getRequestMatcher("b");
						return null;
					}

					@Override
					public Collection<ConfigAttribute> getAllConfigAttributes() {
						return null;
					}

					@Override
					public boolean supports(Class<?> clazz) {
						return FilterInvocation.class.isAssignableFrom(clazz);
					}
				});
				return object;
			}
		}))
			.formLogin(formlogin->formlogin
					.loginPage("/login-licon.html")
					.failureUrl("login-error")
					.successHandler((request, response, authentication) -> {
						PrintWriter writer = response.getWriter();
						writer.println("{code:200,success:true}");
						writer.flush();
					})
					.failureHandler((request, response, exception) -> {
						PrintWriter writer = response.getWriter();
						writer.println("{code:500,success:false}");
						writer.flush();
					})
			);
	}
}
