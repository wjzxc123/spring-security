package com.lut.springsecurity.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.lut.springsecurity.bean.Authority;
import com.lut.springsecurity.bean.Url;
import com.lut.springsecurity.service.IUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 9:47
 */
@Component
@Slf4j
public class CustomerWebMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	IUrlService urlService;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		//TODO 使用redis存储权限集
		List<Url> allUrl = urlService.getUrlByPath(request.getRequestURI());
		if (CollectionUtils.isEmpty(allUrl)){ 
			return SecurityConfig.createList();
		}
		Map<String, List<Authority>> urlsMap = allUrl.stream()
				.collect(Collectors.toMap(Url::getUrlPath, Url::getAuthorities));

		Set<RequestMatcher> requestsSet = allUrl.stream().map(url -> new CustomerAntPathRequestMatcherProvider(path -> {
			if (path.contains(HttpMethod.GET.name())) {
				return new AntPath(path, HttpMethod.GET.name());
			}
			else if (path.contains(HttpMethod.POST.name())) {
				return new AntPath(path, HttpMethod.POST.name());
			}
			else {
				return new AntPath(path, HttpMethod.GET.name());
			}
		}).getRequestMatcher(url.getUrlPath())).collect(Collectors.toSet());

		RequestMatcher requestMatcher = requestsSet.stream().filter(x -> x.matches(request)).findAny()
				.orElseThrow(() -> new AccessDeniedException("非法访问"));
		if (requestMatcher instanceof AntPathRequestMatcher){
			String pattern = ((AntPathRequestMatcher) requestMatcher).getPattern();
			log.info("匹配的请求url=》{}",pattern);
			return SecurityConfig.createList(new HashSet<>(urlsMap.get(pattern))
					.stream().map(Authority::getRoleCode)
					.distinct().toArray(String[]::new));
		}

		return SecurityConfig.createList();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
