package com.lut.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 16:32
 */
@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage(){
		return "/login-licon";
	}
}
