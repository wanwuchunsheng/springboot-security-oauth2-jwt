package com.smartwf.auth.modules.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartwf.auth.modules.dao.UmsAdminDao;
import com.smartwf.auth.modules.dao.UmsPermissionDao;
import com.smartwf.common.pojo.UmsAdmin;
import com.smartwf.common.pojo.UmsPermission;

@Service
public class UmsAddminServiceImpl implements UmsAdminService{
	
	@Autowired
	private UmsAdminDao umsAdminDao;
	
	@Autowired
	private UmsPermissionDao umsPermissionDao;

	@Override
	public UmsAdmin getAdminByUsername(String username) {
		QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		UmsAdmin bean = umsAdminDao.selectOne(queryWrapper);
		return bean;
	}

	@Override
	public List<UmsPermission> getPermissionList(Long id) {
		QueryWrapper<UmsPermission> queryWrapper = new QueryWrapper<>();
		List<UmsPermission> upList = umsPermissionDao.selectList(queryWrapper);
		return upList;
	}

	


}
