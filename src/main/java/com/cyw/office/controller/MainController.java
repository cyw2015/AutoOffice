package com.cyw.office.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyw.office.entity.sys.User;
import com.cyw.office.service.sys.IUserService;
import com.cyw.office.util.CommonUtil;

@Controller
public class MainController {
	@Autowired
	private IUserService userService;
	@RequestMapping("/main.do")
	public String toIndex(HttpServletRequest request, Model model) {
		String usercode = CommonUtil.getLoginUserName();
		User user = userService.findByUserCode(usercode);
		if(user!=null){
			model.addAttribute("username",user.getUserName() );
		}else{
			model.addAttribute("username","");
		}
		return "main";
	}
}
