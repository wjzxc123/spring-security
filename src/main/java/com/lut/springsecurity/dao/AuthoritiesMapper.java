package com.lut.springsecurity.dao;

import com.lut.springsecurity.bean.Authorities;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/30 10:32
 */
@Mapper
public interface AuthoritiesMapper {
	/***
	 *更新用户权限信息
	 * @param authorities
	 * @return {@link int}
	 * @throws
	 * @author Licon
	 * @date 2021/4/30 11:01
	 */
	@Update("update authorities set username = #{authorities.username} authority_id = #{authorities.authorityId} where id= #{authorities.id}")
	int updateAuthorities(@Param("authorities") Authorities authorities);

	/***
	 *添加用户权限信息
	 * @param authorities
	 * @return {@link int}
	 * @throws
	 * @author Licon
	 * @date 2021/4/30 13:20
	 */
	@Insert("insert into authorities(username,authority_id) values(#{authorities.username},#{authorities.authorityId})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	int addAuthorities(@Param("authorities") Authorities authorities);

	
	/***
	 *根据id删除用户权限信息
	 * @param id
	 * @return {@link int}
	 * @throws
	 * @author Licon
	 * @date 2021/4/30 13:54
	 */
	@Delete("delete from authorities where id = #{id}")
	int deleteAuthorities(@Param("id") Integer id);
}
