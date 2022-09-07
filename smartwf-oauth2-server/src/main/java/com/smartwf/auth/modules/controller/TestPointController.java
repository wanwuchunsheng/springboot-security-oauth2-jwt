package com.smartwf.auth.modules.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.smartwf.common.pojo.Result;
import com.smartwf.common.service.IGlobalCache;
import com.smartwf.auth.modules.service.AdminUserDetails;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <p>用户信息接口</p>
 *
 * @author WCH
 * Date 2022/5/10
 * @version 1.0
 */
@RestController
@RequestMapping("/text")
@Log4j2
public class TestPointController {
	
	@Autowired
    private JwtDecoder jwtDecoder;
	
	@Autowired
	private IGlobalCache globalCache;

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/user")
    public Result<?> oauth2UserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 生成新的认证信息
        //Authentication newAuth = new OAuth2AuthenticationToken((OAuth2User) auth.getPrincipal(),adminUserDetails.getAuthorities(),"my_client");
        // 重置认证信息
        //SecurityContextHolder.getContext().setAuthentication(newAuth);
        log.info("____{}",authentication.getAuthorities() );
        
        return Result.success(authentication);
    }
    
    
    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<?> getUserInfo(HttpServletRequest request){
    	String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String authToken = authHeader.substring(7);
            String username = jwtDecoder.decode(authToken).getSubject();
            //redis保存信息
            Object bean = globalCache.get(username);
        	if (bean == null) {
        		Result.failed(HttpStatus.HTTP_UNAUTHORIZED, "认证信息失效，重新登录");
        	}
        	AdminUserDetails userDetails = JSONUtil.toBean(JSONUtil.toJsonStr(bean), AdminUserDetails.class);
        	//赋值权限
            //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //SecurityContextHolder.getContext().setAuthentication(authentication);
        	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            return Result.success(authentication);
        }
        return Result.failed();
    }
}