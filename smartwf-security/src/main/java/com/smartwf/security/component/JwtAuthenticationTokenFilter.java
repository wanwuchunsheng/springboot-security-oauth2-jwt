package com.smartwf.security.component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.IGlobalCache;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * JWT登录授权过滤器
 * @author WCH
 * @Ddatetime 2022年7月7日17:46:48
 */
@Log4j2
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    
	@Autowired
	private UserDetailsService userDetailsService;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String authToken = authHeader.substring(7);
            String username = jwtDecoder.decode(authToken).getSubject();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("____{}",auth.getAuthorities());
            if (username != null ) {
            	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            	/***
            	Object bean = globalCache.get(username);
            	if (bean == null) {
            		Result.failed(HttpStatus.HTTP_UNAUTHORIZED, "认证信息失效，重新登录");
            	}
    	        AdminUserDetails userDetails = JSONUtil.toBean(JSONUtil.toJsonStr(bean), AdminUserDetails.class);
    	        
    	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        		return authorities.stream().map(GrantedAuthority::getAuthority).filter(StringUtils::hasText).anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
    	        
    	        
    	        */
    	        //赋值权限
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}

