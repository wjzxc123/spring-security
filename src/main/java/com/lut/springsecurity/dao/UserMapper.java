package com.lut.springsecurity.dao;

import java.util.List;

import com.lut.springsecurity.bean.User;
import com.lut.springsecurity.entity.UserAuthorities;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/3/31 10:13
 */
@Mapper
public interface UserMapper {

	@Select("select username,password,enabled from users")
	List<User> selectAll();

	/***
	 * 根据用户名查询用户
	 * @param username
	 * @return {@link User}
	 * @throws
	 * @author Licon
	 * @date 2021/4/23 10:35
	 */
	@Select("select username,password,enabled from users where username = #{username} limit 1")
	User selectUserByUserName(@Param("username") String username);


	/***
	 *根据用户名查询用户权限
	 * @param username
	 * @return {@link UserAuthorities}
	 * @throws
	 * @author Licon
	 * @date 2021/4/25 9:46
	 */
	UserAuthorities selectUserAuthByUserName(@Param("username") String username);
}
