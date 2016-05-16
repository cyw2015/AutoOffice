package com.cyw.office.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sys")
public class AuthController {
	@RequestMapping("/auths.do")
	public String toIndex(HttpServletRequest request, Model model) {
		return "sys/auth";
	}
}
