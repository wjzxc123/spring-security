package com.lut.springsecurity.bean;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 9:29
 */
@Data
public class Authority implements GrantedAuthority {
	String id;
	String roleCode;
	String roleName;
	String authType;
	String pCode;

	@Override
	public String getAuthority() {
		return this.roleCode;
	}
}
