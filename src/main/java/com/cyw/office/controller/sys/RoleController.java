package com.cyw.office.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyw.office.service.sys.IRoleService;

@Controller
@RequestMapping(value = "/sys")
public class RoleController {
	@Autowired
	private IRoleService roleService;
}
