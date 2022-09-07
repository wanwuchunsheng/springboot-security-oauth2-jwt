package com.smartwf.security.service;

import com.smartwf.security.component.AdminUserDetails;

/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
public interface UmsAdminService {
	
	/**
     * 获取用户信息
     */
	AdminUserDetails loadUserByUsername(String username);
}
