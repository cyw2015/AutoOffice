package com.cyw.office.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cyw.office.entity.sys.Role;
import com.cyw.office.service.sys.IRoleService;
import com.cyw.office.util.CommonUtil;
import com.cyw.office.util.DateUtil;
import com.cyw.office.util.PageBean;


@Controller
@RequestMapping(value = "/sys")
public class RoleController {
	@Autowired
	private IRoleService roleService;
	@RequestMapping(value = "/roles.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("sys/role");
	}
	
	/** 角色信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getRolePage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getRolePage(HttpServletRequest request,
			HttpServletResponse response) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("start", pageBean.getStart());
		paramsMap.put("end", pageBean.getEnd());
		paramsMap.put("sort", sort);
		paramsMap.put("order", order);
		List<Role> roleList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			roleList = roleService.getRolePage(paramsMap);
			if (roleList != null && roleList.size() != 0) {
				for (Role role : roleList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", role.getId());
					objInfo.put("role_code",role.getRoleCode());
					objInfo.put("role_name", role.getRoleName());
					objInfo.put("remark", role.getRemark());
					objInfo.put("creater", role.getCreater());
					objInfo.put("createrDate", DateUtil.format(role.getCreatedate()));
					objInfo.put("enabled",role.getEnabled());
					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total", roleList.get(0).getTotalCount());
			} else {
				obj.put("rows", "");
				obj.put("total", 0);
			}
		} catch (Exception e) {
			obj.put("rows", "");
			obj.put("total", 0);
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 增加角色信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addRole.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addRole(HttpServletRequest request,HttpServletResponse response){
		String roleCode = request.getParameter("roleCode");
		String roleName = request.getParameter("roleName");
		String remark = request.getParameter("remark");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("roleCode", roleCode);
		paramsMap.put("roleName",roleName);
		paramsMap.put("remark", remark);
		//获取当前登录用户
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		JSONObject obj = new JSONObject();
		try{
			//查询角色是否存在
			Role role = roleService.findByRoleCode(roleCode);
			if(role!=null){
				obj.put("error",'1');
				obj.put("errorMsg","角色编码已存在");
				return obj;
			}else{
				int i = roleService.insert(paramsMap);
				if(i!=0){
					obj.put("error",'0');
				}else{
					obj.put("error",'1');
					obj.put("errorMsg","插入失败");
				}
			}
		}catch(Exception e){
			obj.put("error",'1');
			obj.put("errorMsg","插入失败");
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 修改角色信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateRole.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateRole(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String roleCode = request.getParameter("roleCode");
		String roleName = request.getParameter("roleName");
		String remark = request.getParameter("remark");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("roleCode", roleCode);
		paramsMap.put("roleName",roleName);
		paramsMap.put("remark", remark);
		try{
			int result=roleService.updateByRoleCode(paramsMap);
			if(result!=0){
				obj.put("error",'0');
			}else{
				obj.put("error",'1');
				obj.put("errorMsg","修改失败");
			}
		}catch(Exception e){
			obj.put("error",'1');
			obj.put("errorMsg","修改失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteRoles.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteRoles(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String[] roleCodes = ids.split(","); 
		try{
			int result=roleService.deleteRolesByIds(roleCodes);
			if(result!=0){
				obj.put("error",'0');
			}else{
				obj.put("error",'1');
				obj.put("errorMsg","删除失败");
			}
		}catch(Exception e){
			obj.put("error",'1');
			obj.put("errorMsg","删除失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 配置角色的权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/configAuth.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject configAuth(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		String roleCode = request.getParameter("roleCode");
		String authCode = request.getParameter("authCodes");
		String[] authCodes;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(authCode!=null&&authCode.trim().length()!=0){
			authCodes= authCode.split(",");
			for (int i = 0; i < authCodes.length; i++) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("roleCode", roleCode);
				paramsMap.put("authCode", authCodes[i]);
				paramsMap.put("creater", CommonUtil.getLoginUserName());
				paramsMap.put("clean", "0");
				list.add(paramsMap);
			}
		}else{
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("roleCode", roleCode);
			paramsMap.put("clean", "1");
			list.add(paramsMap);
		}
		
		try {
			roleService.configAuth(list);
			obj.put("error", '0');
		} catch (Exception e) {
			obj.put("error", '1');
			obj.put("errorMsg", "配置权限失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 获取配置角色信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getConfigRole.do")
	@ResponseBody
	public JSONArray getConfigRole(HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray arr = new JSONArray();
		String userCode = request.getParameter("userCode");
		List<Role> roleList = null;
		try {
			roleList = roleService.getConfigRole(userCode);
			if(roleList!=null&&roleList.size()>0){
				for(Role role :roleList){
					JSONObject obj = new JSONObject();
					obj.put("roleCode", role.getRoleCode());
					obj.put("roleName", role.getRoleName());
					obj.put("check", role.getRemark()!=null&&role.getRemark().equals("check")?true:false);
					arr.add(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
}
