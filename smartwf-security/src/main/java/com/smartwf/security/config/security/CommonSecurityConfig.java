package com.smartwf.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smartwf.security.component.JwtAuthenticationTokenFilter;
import com.smartwf.security.component.RestAuthenticationEntryPoint;
import com.smartwf.security.component.RestfulAccessDeniedHandler;
import com.smartwf.security.service.UmsAdminService;

@Configuration
public class CommonSecurityConfig{
	
	@Autowired
	private UmsAdminService umsAdminService;
	
	@Bean
    public UserDetailsService userDetailsService() {
		//获取登录用户信息
        return username -> umsAdminService.loadUserByUsername(username);
    }
	
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }
    
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
    
    /*** --------------
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
    */
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    */
    
    
	
    
    
}
