package com.lut.springsecurity.controller;

import java.util.Collection;
import java.util.List;

import com.lut.springsecurity.bean.Authorities;
import com.lut.springsecurity.bean.User;
import com.lut.springsecurity.service.IUserService;
import com.lut.springsecurity.util.SecurityContextHolderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


	@GetMapping("/update-authority/{id}/{authority_id}")
	public String updateAuthority(@PathVariable("id")Integer id,@PathVariable("authority_id")Integer authorityId){
		boolean authority = SecurityContextHolderUtil.isAuthority("admin");
		if (authority){
			String userName = SecurityContextHolderUtil.getUserName();
			Authorities build = Authorities.builder()
					.id(id)
					.username(userName)
					.authorityId(authorityId)
					.build();
			iUserService.updateAuthorities(build);
		}
		return "success";
	}
}
