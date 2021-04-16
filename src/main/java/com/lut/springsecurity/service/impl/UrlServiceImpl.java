package com.lut.springsecurity.service.impl;

import java.util.List;

import com.lut.springsecurity.bean.Url;
import com.lut.springsecurity.dao.UrlMapper;
import com.lut.springsecurity.service.IUrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 9:10
 */
@Service
public class UrlServiceImpl implements IUrlService {
	@Autowired
	UrlMapper urlMapper;

	@Override
	public List<Url> getAllUrl() {
		return urlMapper.selectAllUrl();
	}

	@Override
	public List<Url> getUrlByPath(String path) {
		return null;
	}
}
