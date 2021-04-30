package com.lut.springsecurity.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/25 16:36
 */
public class ProductBcrypt {
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bCryptPasswordEncoder.encode("123456"));
	}
}
