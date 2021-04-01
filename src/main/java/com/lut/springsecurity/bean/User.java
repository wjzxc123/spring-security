package com.lut.springsecurity.bean;

import lombok.Data;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 10:17
 */
@Data
public class User {
	String username;
	String password;
	String enabled;
}
