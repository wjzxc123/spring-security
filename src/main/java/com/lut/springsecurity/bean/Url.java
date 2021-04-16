package com.lut.springsecurity.bean;

import java.util.List;

import lombok.Data;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/1 11:44
 */
@Data
public class Url {
	String id;
	String urlPath;
	String describe;
	int voteType;
	List<Authority> authorities;
}
