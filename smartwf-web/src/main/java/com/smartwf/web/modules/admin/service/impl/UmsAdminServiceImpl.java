package com.smartwf.web.modules.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.smartwf.common.service.IGlobalCache;
import com.smartwf.security.component.AdminUserDetails;
import com.smartwf.web.modules.admin.service.UmsAdminService;

import cn.hutool.json.JSONUtil;




@Service
public class UmsAdminServiceImpl implements UmsAdminService{

	@Autowired
	private IGlobalCache globalCache;
	
	@Override
	public AdminUserDetails loadUserByUsername(String username) {
		Object bean = globalCache.get(username);
        AdminUserDetails userDetails = JSONUtil.toBean(JSONUtil.toJsonStr(bean), AdminUserDetails.class);
        return userDetails;
	}

}
