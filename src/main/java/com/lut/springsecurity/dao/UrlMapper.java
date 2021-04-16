package com.lut.springsecurity.dao;

import java.util.List;

import com.lut.springsecurity.bean.Url;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 9:11
 */
@Mapper
public interface UrlMapper {
	List<Url> selectAllUrl();

	List<Url> getUrlByPath(@Param("path") String path);
}
