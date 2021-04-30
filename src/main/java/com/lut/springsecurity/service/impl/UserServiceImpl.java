package com.lut.springsecurity.service.impl;

import java.util.List;

import com.lut.springsecurity.bean.Authorities;
import com.lut.springsecurity.bean.Authority;
import com.lut.springsecurity.bean.Url;
import com.lut.springsecurity.bean.User;
import com.lut.springsecurity.dao.AuthoritiesMapper;
import com.lut.springsecurity.dao.UrlMapper;
import com.lut.springsecurity.dao.UserMapper;
import com.lut.springsecurity.entity.UserAuthorities;
import com.lut.springsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 10:19
 */
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserMapper userMapper;

	@Autowired
	AuthoritiesMapper authoritiesMapper;

	@Override
	public List<User> selectAll() {
		return userMapper.selectAll();
	}

	@Override
	public User selectUserByUserName(String username) {
		return userMapper.selectUserByUserName(username);
	}

	@Override
	public UserAuthorities selectUserAuthByUserName(String username) {
		return userMapper.selectUserAuthByUserName(username);
	}

	@Override
	public int updateAuthorities(Authorities authorities) {
		return authoritiesMapper.updateAuthorities(authorities);
	}
}
