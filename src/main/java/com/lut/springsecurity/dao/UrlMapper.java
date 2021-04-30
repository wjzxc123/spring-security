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
	/***
	 * 查询所有的URL包括权限
	 * @param 
	 * @return {@link List<Url>}
	 * @throws
	 * @author Licon
	 * @date 2021/4/27 10:34
	 */
	List<Url> selectAllUrl();

	/***
	 *根据路径匹配查询URL及权限
	 * @param path
	 * @return {@link List<Url>}
	 * @throws
	 * @author Licon
	 * @date 2021/4/27 10:34
	 */
	List<Url> getUrlByPath(@Param("path") String path);
}
