package com.smartwf.sm.modules.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartwf.common.pojo.Result;
import com.smartwf.sm.modules.admin.pojo.UserInfo;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用户资料控制层
 * @author WCH
 * @Date: 2019-11-27 11:25:24
 */
@RestController
@RequestMapping("user")
@Slf4j
@Api( tags = "用户资料控制器")
public class UserInfoController {
	
	
	
	/**
	 * @Description: 查询用户资料分页
	 * @return
	 */
    @GetMapping("selectUserInfoByPage")
    @ApiOperation(value = "分页查询接口", notes = "分页查询用户资料")
    @PreAuthorize("hasAuthority('pms:product:read')")
    public Result<?> selectUserInfoByPage(Page<UserInfo> page, UserInfo bean) {
        try {
            Result<?> result = null;
            
            return Result.success(null);
            
        } catch (Exception e) {
            log.error("分页查询用户资料信息错误！{}", e.getMessage(), e);
        }
        return Result.failed(HttpStatus.HTTP_BAD_REQUEST,"分页查询用户资料信息错误！");
    }
    
    /**
     * @Description: 主键查询用户资料
     * @return
     */
    @GetMapping("selectUserInfoById")
    @ApiOperation(value = "主键查询接口", notes = "主键查询用户资料")
    @PreAuthorize("hasAuthority('aa')")
    public Result<?> selectSysUserById(UserInfo bean) {
        try {
        	 Result<?> result = null;
             
             return Result.success(null);
        } catch (Exception e) {
            log.error("主键查询用户资料信息错误！{}", e.getMessage(), e);
        }
        return Result.failed(HttpStatus.HTTP_BAD_REQUEST,"主键查询用户信息错误！");
    }
    
   
}
