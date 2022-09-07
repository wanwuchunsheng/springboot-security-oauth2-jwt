package com.smartwf.resb.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import cn.hutool.json.JSONUtil;
import javassist.expr.NewArray;

@Service
public class UmsAddminServiceImpl implements UmsAdminService{
	
	@Autowired
	private IGlobalCache globalCache;

	@Override
	public UserDetails loadUserByUsername(String username) {
	        // 根据三方用户查绑定的本地用户
	        Object bean = globalCache.get(username);
	        if (JSONUtil.isNull(bean)) {
	        	return null;
	        }
	        
	        AdminUserDetails userDetails = JSONUtil.toBean(JSONUtil.toJsonStr(bean), AdminUserDetails.class);
	        // 生成新的认证信息
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	        // 重置认证信息
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return userDetails;

	}

}
