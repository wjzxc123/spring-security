package com.lut.springsecurity.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/16 10:46
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	LiconUserDetailService userDetailService;

	@Resource
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String)authentication.getCredentials();
		UserDetails userDetails = userDetailService.loadUserByUsername(username);
		if (userDetails == null){
			throw new UsernameNotFoundException("用户不存在");
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())){
			throw new BadCredentialsException("密码不正确！");
		}

		return new UsernamePasswordAuthenticationToken(username,password, new HashSet<>(userDetails.getAuthorities()));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
