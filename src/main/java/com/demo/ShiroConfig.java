package com.demo;

import com.demo.system.shiro.RetryLimitHashedCredentialsMatcher;
import com.demo.system.shiro.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * 
 * @author sdyang
 * @date 2016年1月22日 上午11:10:51
 */
@Configuration
public class ShiroConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Filter> filters = new HashMap<String, Filter>();
	private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}

	@Bean(name = "credentialsMatcher")
	public RetryLimitHashedCredentialsMatcher getCredentialsMatcher() {
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(
				getEhCacheManager());
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;

	}

	@Bean(name = "userRealm")
	public UserRealm getUserRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(getCredentialsMatcher());
		userRealm.setCachingEnabled(false);
		return userRealm;
	}

	@Bean(name = "shiroEhcacheManager")
	public EhCacheManager getEhCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return em;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getSecurityManager() {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(getUserRealm());
		dwsm.setCacheManager(getEhCacheManager());
		return dwsm;
	}

	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
		MethodInvokingFactoryBean methodInvoking = new MethodInvokingFactoryBean();
		methodInvoking
				.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvoking
				.setArguments(new DefaultWebSecurityManager[] { getSecurityManager() });
		return methodInvoking;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(getSecurityManager());
		return new AuthorizationAttributeSourceAdvisor();
	}

	@Bean(name = "formAuthenticationFilter")
	public FormAuthenticationFilter getFormAuthenticationFilter() {
		FormAuthenticationFilter formFilter = new FormAuthenticationFilter();
		formFilter.setUsernameParam("username");
		formFilter.setPasswordParam("password");
		formFilter.setLoginUrl("/login");
		return formFilter;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/manager/list.html");

		filters.put("authc", getFormAuthenticationFilter());

		shiroFilterFactoryBean.setFilters(filters);
		filterChainDefinitionMap.put("/templates/**", "authc");
		filterChainDefinitionMap.put("/static/**", "anon");
		shiroFilterFactoryBean
				.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public ExceptionHandlerExceptionResolver getExceptionHandler() {
		ExceptionHandlerExceptionResolver eh = new ExceptionHandlerExceptionResolver();
		return eh;
	}

//	@Bean
//	public DefaultExceptionHandler gethandler() {
//		DefaultExceptionHandler handler = new DefaultExceptionHandler();
//		return handler;
//	}
}
