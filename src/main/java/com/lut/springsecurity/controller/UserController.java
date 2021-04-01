package com.lut.springsecurity.controller;

import java.util.List;

import com.lut.springsecurity.bean.User;
import com.lut.springsecurity.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 10:12
 */
@RestController
public class UserController {

	@Autowired
	IUserService iUserService;

	@GetMapping("/all-user")
	public List<User> allUser(){
		return iUserService.selectAll();
	}
}
