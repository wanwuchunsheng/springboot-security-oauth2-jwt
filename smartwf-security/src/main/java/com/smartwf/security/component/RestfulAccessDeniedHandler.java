package com.smartwf.security.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.smartwf.common.pojo.Result;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;

/**
 * 当访问接口没有权限时，自定义的返回结果
 * @author WCH
 * @Ddatetime 2022年7月7日17:46:48
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request,HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Result.failed(HttpStatus.HTTP_FORBIDDEN, e.getMessage())));
        response.getWriter().flush();
    }

}
