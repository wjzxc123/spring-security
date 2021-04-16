package com.lut.springsecurity.config;

import java.net.URL;

import com.lut.springsecurity.bean.Url;

import org.springframework.security.access.AccessDecisionManager;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 10:44
 */
@FunctionalInterface
public interface IDecisionManageProvider {
	AccessDecisionManager getAccessDecisionManager(Integer url);
}
