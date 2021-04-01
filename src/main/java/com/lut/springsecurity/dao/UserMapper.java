package com.lut.springsecurity.dao;

import java.util.List;

import com.lut.springsecurity.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 10:13
 */
@Mapper
public interface UserMapper {

	@Select("select * from users")
	List<User> selectAll();
}
