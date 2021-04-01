package com.lut.springsecurity.service;

import java.util.List;

import com.lut.springsecurity.bean.User;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 10:19
 */
public interface IUserService {
	List<User> selectAll();
}
