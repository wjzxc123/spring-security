package com.lut.springsecurity.bean;

import lombok.Builder;
import lombok.Data;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/30 10:33
 */
@Data
@Builder
public class Authorities {
	Integer id;
	String username;
	Integer authorityId;
}
