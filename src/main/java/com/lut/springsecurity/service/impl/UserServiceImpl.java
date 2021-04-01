package com.lut.springsecurity.service.impl;

import java.util.List;

import com.lut.springsecurity.bean.User;
import com.lut.springsecurity.dao.UserMapper;
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

	@Override
	public List<User> selectAll() {
		return userMapper.selectAll();
	}
}
