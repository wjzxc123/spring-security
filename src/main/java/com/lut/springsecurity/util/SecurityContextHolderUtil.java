package com.lut.springsecurity.util;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/30 16:23
 */
public class SecurityContextHolderUtil {
	public static Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static String getUserName(){
		return getAuthentication().getName();
	}

	public static Collection<? extends GrantedAuthority> getAuthorities(){
		 return getAuthentication().getAuthorities();
	}

	public static void setAuthentication(Authentication authentication){
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static void clearAuthentication(){
		SecurityContextHolder.clearContext();
	}

	public static boolean isAuthority(String ... authorities){
		Collection<? extends GrantedAuthority> hasAuthorities = getAuthorities();
		for (GrantedAuthority hasAuthority : hasAuthorities) {
			for (String authority : authorities) {
				if (authority.equals(hasAuthority.getAuthority())){
					return true;
				}
			}
		}
		return false;
	}
}
