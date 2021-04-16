package com.lut.springsecurity.service;

import java.util.List;

import com.lut.springsecurity.bean.Url;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/1 11:44
 */
public interface IUrlService {
	List<Url> getAllUrl();

	List<Url> getUrlByPath(String path);
}
