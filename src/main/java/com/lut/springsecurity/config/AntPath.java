package com.lut.springsecurity.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/1 11:26
 */
@Getter
@Setter
@AllArgsConstructor
public class AntPath {
	String path;

	String method;
}
