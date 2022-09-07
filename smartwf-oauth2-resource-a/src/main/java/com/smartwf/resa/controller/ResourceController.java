package com.smartwf.resa.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;

@RestController
public class ResourceController {
	
	@GetMapping("/res1")
	//@PreAuthorize("hasAuthority('pms:product:read')")
    @ResponseBody
	public Result<?> getRes1() {
		return Result.success(null,"服务A-》资源1");
	}
	
	@GetMapping("/res2")
	//@PreAuthorize("hasAuthority('pms:product:read')")
    @ResponseBody
	public Result<?> getRes2() {
		return Result.success(null,"服务B-》资源2");
	}

}
