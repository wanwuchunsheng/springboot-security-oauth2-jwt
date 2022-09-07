package com.smartwf.web.modules.admin.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.smartwf.security.component.AdminUserDetails;
import com.smartwf.web.modules.admin.pojo.PmsProduct;
import com.smartwf.web.modules.admin.pojo.UmsAdmin;
import com.smartwf.web.modules.admin.pojo.UmsPermission;

/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
public interface UmsAdminService {


   
	
	/**
     * 获取用户信息
     */
	UserDetails loadUserByUsername(String username);
}
