package com.lut.springsecurity.entity;

import java.util.List;

import com.lut.springsecurity.bean.Authority;
import lombok.Data;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/25 9:15
 */
@Data
public class UserAuthorities {
	String username;
	List<Authority> authorityList;
}
