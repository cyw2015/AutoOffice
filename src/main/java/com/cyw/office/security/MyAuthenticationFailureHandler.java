package com.cyw.office.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class MyAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		String f = request.getParameter("f");
		if (StringUtils.isNotEmpty(f)) {
			if (f.equals("android")) {
				response.setContentType("text/html;charset=UTF-8");
//				response.setCharacterEncoding("UTF-8");
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("error", "1");
				resultMap.put("errorMsg", "账号或密码有误,登录失败");
				response.getWriter().write(JSONObject.fromObject(resultMap).toString());
			}
		} else {
			request.getRequestDispatcher("/login.jsp?error=true").forward(request,response);
		}
	}

}
