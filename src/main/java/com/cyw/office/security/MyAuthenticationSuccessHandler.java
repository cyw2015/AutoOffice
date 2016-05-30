package com.cyw.office.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication arg2)
			throws IOException, ServletException {
		String f = request.getParameter("f");
		if (StringUtils.isNotEmpty(f)) {
			if (f.equals("android")) {
				response.setContentType("text/html;charset=UTF-8");
//				response.setCharacterEncoding("UTF-8");
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("error", "0");
				resultMap.put("errorMsg", "");
				response.getWriter().write(JSONObject.fromObject(resultMap).toString());
			}
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request,response);
		}
	}

}
