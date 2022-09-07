package com.smartwf.sm.modules.admin.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.sm.modules.admin.pojo.UserInfo;
import com.smartwf.sm.modules.admin.vo.UserInfoVO;

/**
 * @Date: 2019-11-27 11:29:02
 * @Description: 用户资源持久层接口
 * @author WCH
 */
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {

	/**
	 * 
	 * 查询用户资源分页
	 * @param page 
	 * @param bean
	 * @return
	 */
	List<UserInfo> selectUserInfoByPage(@Param("bean") UserInfo bean, Page<UserInfo> page);

	
	/**
	 * 主键查询用户资料
	 * @param bean
	 * @return
	 */
	UserInfo selectUserInfoById(@Param("bean") UserInfo bean);

	
	
	
    
}
