package com.cyw.office.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
//	@Autowired
//	private IUserService userService;

	@RequestMapping("/showUser.do")
	public String toIndex(HttpServletRequest request, Model model) {
		int userId = Integer.parseInt(request.getParameter("id"));
//		User user = this.userService.getUserById(new BigDecimal(userId));
//		model.addAttribute("user", user);
//		System.out.println(user.toString());
		return "showUser";
	}
}
