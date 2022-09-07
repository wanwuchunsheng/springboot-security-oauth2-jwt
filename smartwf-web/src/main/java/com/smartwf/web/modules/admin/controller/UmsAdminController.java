package com.smartwf.web.modules.admin.controller;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartwf.common.pojo.Result;
import com.smartwf.web.modules.admin.dto.UmsAdminLoginParam;
import com.smartwf.web.modules.admin.pojo.UmsAdmin;
import com.smartwf.web.modules.admin.service.UmsAdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * 后台用户管理
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "后台用户管理")
@RequestMapping("/admin")
@Log4j2
public class UmsAdminController {
	
	@Autowired
    private UmsAdminService adminService;
	
	/***
	@Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return Result.failed();
        }
        return Result.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        Boolean  flag = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        
        //先拿code,
        try {
            OAuthClientRequest oauthResponse = OAuthClientRequest
                    .authorizationLocation("http://os.com:9000/smartwf-auth-server/oauth2/authorize")
                    .setResponseType(OAuth.OAUTH_CODE)
                    .setClientId("my_client")
                    .setRedirectURI("http://127.0.0.1:8000")
                    .setScope("read")
                    .buildQueryMessage();
           
            log.info("{}",oauthResponse);
            return "redirect:" + oauthResponse.getLocationUri();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return null;
        
    }
   */
	
	/**
     * 获取商品列表
     * 
     * */
    @ApiOperation("获取test")
    @GetMapping(value = "/test")
    @PreAuthorize("hasAuthority('pms:product:read')")
    public Result<?> test(String code) {
    	Authentication t =SecurityContextHolder.getContext().getAuthentication();
    	log.info(">>>>>>>________{}",t);
    	
    	return Result.success(t);
    }
    
    /**
     * 获取商品列表
     * 
     * */
    @ApiOperation("获取accessToken")
    @GetMapping(value = "/getToken")
    @PreAuthorize("hasAuthority('pms:product:read')")
    @ResponseBody
    public Result<?> queryProductList(String code) {
    	Authentication t =SecurityContextHolder.getContext().getAuthentication();
    	log.info(">>>>>>>________{}",t);
    	//再取token
    	/***
        try {
			 OAuthClientRequest request = OAuthClientRequest
		               .tokenLocation("http://os.com:9000/smartwf-auth-server/oauth2/token")
		               .setClientId("my_client")
		               .setClientSecret("$2a$10$ZcH/Qu3Sd53HHh5S/a3N0Oz2sLxnzMgWqDnFhEl8Q7Oc6rYQfl4Ga")
		               .setGrantType(GrantType.AUTHORIZATION_CODE)
		               .setCode(code)
		               .setRedirectURI("http://127.0.0.1:8000")
		               .buildBodyMessage();

		       OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		       OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);

		       System.out.println("Access Token: " + oauthResponse.getAccessToken());
		       System.out.println("Expires In: " + oauthResponse.getExpiresIn());
	        return Result.success(oauthResponse);
		} catch (Exception e) {
			log.error("code换取accessToken异常！{}",e.getMessage(),e);
		}
		*/
        return Result.success("OK");
    }
    
    

}
