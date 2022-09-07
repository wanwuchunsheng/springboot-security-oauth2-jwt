package com.smartwf.sm.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import com.smartwf.security.component.RestAuthenticationEntryPoint;
import com.smartwf.security.component.RestfulAccessDeniedHandler;

/**
 * SpringSecurity 5.4.x以上新用法配置
 * 为避免循环依赖，仅用于配置HttpSecurity
 * Created by macro on 2022/5/19.
*/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)	// 启用方法级别的权限认证 Ehcache 3
@Order(-1)
public class SystemSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;	
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	// 基于 token，不需要 csrf
    	httpSecurity.csrf().disable()
    	// 基于token，所以不需要session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        // 允许对于网站静态资源的无授权访问
        .authorizeRequests().antMatchers(HttpMethod.GET, 
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources/**",
                "/v2/api-docs/**"
        ).permitAll()
        // 对登录注册要允许匿名访问
        .antMatchers("/admin/login", "/admin/register","/admin/getToken").permitAll()
        // 跨域请求会先进行一次options请求
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        // 除上面外的所有请求全部需要鉴权认证
        .anyRequest().authenticated().and()
        // 资源服务JWT认证
        .oauth2ResourceServer(resourceServer -> resourceServer
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .jwt()
        );
		// 禁用缓存
		httpSecurity.headers().cacheControl();
		//添加自定义未授权和未登录结果返回
		httpSecurity.exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint);
        return httpSecurity.build();
    }
    
	@Bean
    public UserDetailsService userDetailsService() {
		//获取登录用户信息
        return username -> userDetailsService.loadUserByUsername(username);
    }

} 