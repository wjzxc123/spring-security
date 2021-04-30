package com.lut.springsecurity.config;

import java.util.function.Predicate;

import com.lut.springsecurity.bean.User;
import com.lut.springsecurity.entity.UserAuthorities;
import com.lut.springsecurity.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/16 16:53
 */
@Component
public class LiconUserDetailService implements UserDetailsService {

	@Autowired
	IUserService iUserService;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = iUserService.selectUserByUserName(username);
		if (user == null){
			throw new UsernameNotFoundException("用户不存在");
		}
		UserAuthorities userAuthorities = iUserService.selectUserAuthByUserName(username);

		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
										true,true,true,
										transform(user.getEnabled(), "1"::equalsIgnoreCase),
										userAuthorities.getAuthorityList());
	}

	private boolean transform(String param,Predicate<String> predicate){
		return predicate.test(param);
	}
}
