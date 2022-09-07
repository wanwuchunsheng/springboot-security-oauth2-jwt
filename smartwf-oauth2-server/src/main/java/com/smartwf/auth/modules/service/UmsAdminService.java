package com.smartwf.auth.modules.service;

import java.util.List;

import com.smartwf.common.pojo.UmsAdmin;
import com.smartwf.common.pojo.UmsPermission;

public interface UmsAdminService {

	UmsAdmin getAdminByUsername(String username);

	List<UmsPermission> getPermissionList(Long id);

}
