package com.lut.springsecurity.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.Assert;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 13:49
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomerWebMetadataSource metadataSource;

	@Autowired
	LoginAuthenticationProvider loginAuthenticationProvider;

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/resources/static/**");
	}

	/**
	 *配置内存中的用户，此配置与下面的自定义的用户令牌冲突，只能存在一个，当authenticationManager() 存在的时候这个不起作用
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(loginAuthenticationProvider);
	}

	/**
	 * description 通过springboot的官方文档可以知道
	 *              默认设置AuthenticationManager只有一个用户（'用户'用户名和随机密码，在应用程序启动时以INFO级别打印）
	 *              通过AuthenticationManager理论上可以控制用户校验管理
	 * 				使用自定义的令牌可以实现访问jdbc和其他的设置
	 */
//	@Override
//	protected AuthenticationManager authenticationManager() throws Exception {
//		return new AuthenticationManager() {
//			@Override
//			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//				return null;
//			}
//		};
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorise-> authorise.antMatchers("/login","/index","/static/*").permitAll()
			.anyRequest().authenticated().withObjectPostProcessor(filterSecurityInterceptorObjectPostProcessor()))
			.formLogin(formlogin->formlogin
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/index")
					.failureUrl("/failure")
					.successHandler((request, response, authentication) -> {
						response.setCharacterEncoding("utf-8");
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						PrintWriter writer = response.getWriter();
						writer.println("{\"code\":200,\"success\":true,\"authentication\":"+authentication.getPrincipal()+"}");
						writer.flush();
						writer.close();
					})
					.failureHandler((request, response, exception) -> {
						response.setCharacterEncoding("utf-8");
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						PrintWriter writer = response.getWriter();
						writer.println("{\"code\":500,\"success\":false,\"exception\":"+exception.getMessage()+"}");
						writer.flush();
						writer.close();
					})
			);
			http.exceptionHandling()
					.authenticationEntryPoint((request, response, authException) -> {
						response.setCharacterEncoding("utf-8");
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						PrintWriter writer = response.getWriter();
						writer.println("{\"code\":401,\"success\":false,\"exception\":\""+authException.getMessage()+"\"}");
						writer.flush();
						writer.close();
					})
					.accessDeniedHandler((request, response, accessDeniedException) ->{
						response.setCharacterEncoding("utf-8");
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						PrintWriter writer = response.getWriter();
						writer.println("{code:503,success:false,exception:"+accessDeniedException.getMessage()+"}");
						writer.flush();
						writer.close();
					}
			);

			http.csrf().disable();
		/* 资源服务器jwt配置 .oauth2ResourceServer(oauth2->oauth2.jwt(jwt->jwt.jwtAuthenticationConverter()))*/
	}

	ObjectPostProcessor<FilterSecurityInterceptor> filterSecurityInterceptorObjectPostProcessor(){
		return new ObjectPostProcessor<FilterSecurityInterceptor>() {
			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O object) {
				object.setAccessDecisionManager(obtainAccessDecisionManager());
				object.setSecurityMetadataSource(metadataSource);
				return object;
			}
		};
	}

	AccessDecisionManager obtainAccessDecisionManager(){
		return new CustomerDecisionManageProvider(voteCode->{
			AccessDecisionManager accessDecisionManager = null;
			CustomRoleVoter roleVoter = new CustomRoleVoter();
			switch (VoteTypeEnum.getVoteTypeByCode(voteCode)){
				case AFFIRMATIVE:
					accessDecisionManager = new AffirmativeBased(Collections.singletonList(roleVoter));break;
				case CONSENSUS:
					accessDecisionManager = new ConsensusBased(Collections.singletonList(roleVoter));break;
				case UNANIMOUS:
					accessDecisionManager = new UnanimousBased(Collections.singletonList(roleVoter));break;
				default:
					Assert.isTrue(true,"VoteTypeEnum is IllegalArgumentException");
			}
			return accessDecisionManager;
		}).getAccessDecisionManager(1);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
