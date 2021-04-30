package com.lut.springsecurity.service;

import java.util.List;

import com.lut.springsecurity.bean.Authorities;
import com.lut.springsecurity.bean.Authority;
import com.lut.springsecurity.bean.User;
import com.lut.springsecurity.entity.UserAuthorities;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 10:19
 */
public interface IUserService {
	List<User> selectAll();

	User selectUserByUserName(String username);

	UserAuthorities selectUserAuthByUserName(String username);

	int updateAuthorities(Authorities authorities);
}
