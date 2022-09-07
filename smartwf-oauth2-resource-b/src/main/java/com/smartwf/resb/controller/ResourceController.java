package com.smartwf.resb.controller;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartwf.common.pojo.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/admin")
@Slf4j
@Api( tags = "用户资料控制器")
public class ResourceController {
	
	
	@GetMapping("/login")
    @ResponseBody
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query", name = "code", value = "授权编码", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "myClient", value = "客户端ID", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "redirectUri", value = "重定向地址", dataType = "String"),
        @ApiImplicitParam(paramType = "query", name = "granType", value = "授权码模式类型", dataType = "String")
	})
	public Result<?> adminLogin(String code ) {
		try {
			
			 OAuthClientRequest request = OAuthClientRequest
		               .tokenLocation("http://os.com:9000/smartwf-auth-server/oauth2/token")
		               .setClientId("my_client")
		               .setClientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"))
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
		return Result.success(null,"服务A-》资源1");
	}
	
	
	@GetMapping("/res1")
	//@PreAuthorize("hasAuthority('auth:admin:res1')")
    @ResponseBody
	public Result<?> getRes1() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return Result.success(null,"服务A-》资源1");
	}
	
	@GetMapping("/res2")
	@PreAuthorize("hasAuthority('auth:admin:res1')")
    @ResponseBody
	public Result<?> getRes2() {
		return Result.success(null,"服务B-》资源2");
	}

}
