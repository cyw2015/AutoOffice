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

import com.cyw.office.entity.sys.User;
import com.cyw.office.service.sys.IUserService;
import com.cyw.office.util.CommonUtil;
import com.cyw.office.util.DateUtil;
import com.cyw.office.util.MD5Util;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/sys")
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/users.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("sys/user");
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getUserPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getUserPage(HttpServletRequest request,
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
		List<User> userList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			userList = userService.getUserPage(paramsMap);
			if (userList != null && userList.size() != 0) {
				for (User user : userList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", user.getId());
					objInfo.put("user_code", user.getUserCode());
					objInfo.put("user_name", user.getUserName());
					objInfo.put("user_password", user.getUserPassword());
					objInfo.put("enabled", user.getEnabled());
					objInfo.put("issys", user.getIssys());
					objInfo.put("creater", user.getCreater());
					if (user.getCreatedate() != null)
						objInfo.put("createdate",
								DateUtil.format(user.getCreatedate()));
					if (user.getDeadline() != null)
						objInfo.put("deadline", DateUtil.format(user.getDeadline()));
					if (user.getLastLogin() != null)
						objInfo.put("last_login",
								DateUtil.format(user.getLastLogin()));
					objInfo.put("login_count", user.getLoginCount());
					objInfo.put("remark", user.getRemark());

					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total", userList.get(0).getTotalCount());
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
	
	@RequestMapping(value = "/addUser.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addUser(HttpServletRequest request,HttpServletResponse response){
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		String enabled = request.getParameter("enabled");
		String isSys = request.getParameter("isSys");
		String deadLine = request.getParameter("deadLine");
		String remark = request.getParameter("remark");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userCode", userCode);
		paramsMap.put("userName",userName);
		paramsMap.put("password", MD5Util.string2MD5(userCode));
		paramsMap.put("enabled", enabled);
		paramsMap.put("isSys", isSys);
		paramsMap.put("deadLine", deadLine);
		paramsMap.put("remark", remark);
		//获取当前登录用户
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		JSONObject obj = new JSONObject();
		try{
			//查询账号是否存在
			User user = userService.findByUserCode(userCode);
			if(user!=null){
				obj.put("error",'1');
				obj.put("errorMsg","账号已存在");
				return obj;
			}else{
				userService.insert(paramsMap);
				obj.put("error",'0');
			}
			//插入
		}catch(Exception e){
			obj.put("error",'1');
			obj.put("errorMsg","插入失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	@RequestMapping(value = "/updateUser.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		String enabled = request.getParameter("enabled");
		String isSys = request.getParameter("isSys");
		String deadLine = request.getParameter("deadLine");
		String remark = request.getParameter("remark");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userCode", userCode);
		paramsMap.put("userName",userName);
		paramsMap.put("enabled", enabled);
		paramsMap.put("isSys", isSys);
		paramsMap.put("deadLine", deadLine);
		paramsMap.put("remark", remark);
		try{
			int result=userService.updateByPrimaryKey(paramsMap);
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
	
	@RequestMapping(value = "/resetPassword.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject resetPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String userCode = request.getParameter("userCode");
		String password=MD5Util.string2MD5(userCode);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userCode", userCode);
		paramsMap.put("password",password);
		try{
			int result=userService.resetPassword(paramsMap);
			if(result!=0){
				obj.put("error",'0');
			}else{
				obj.put("error",'1');
				obj.put("errorMsg","重置密码失败");
			}
		}catch(Exception e){
			obj.put("error",'1');
			obj.put("errorMsg","重置密码失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	@RequestMapping(value = "/deleteUsers.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteUsers(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String[] userCodes = ids.split(","); 
		try{
			int result=userService.deleteUsers(userCodes);
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
	 * 配置用户的角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/configRoleSave.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject configRoleSave(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		String userCode = request.getParameter("userCode");
		String roleCode = request.getParameter("roleCodes");
		String[] roleCodes;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(roleCode!=null&&roleCode.trim().length()!=0){
			roleCodes= roleCode.split(",");
			for (int i = 0; i < roleCodes.length; i++) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("userCode", userCode);
				paramsMap.put("roleCode", roleCodes[i]);
				paramsMap.put("creater", CommonUtil.getLoginUserName());
				paramsMap.put("clean", "0");
				list.add(paramsMap);
			}
		}else{
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("userCode", userCode);
			paramsMap.put("clean", "1");
			list.add(paramsMap);
		}
		
		try {
			userService.configRoleSave(list);
			obj.put("error", '0');
		} catch (Exception e) {
			obj.put("error", '1');
			obj.put("errorMsg", "配置权限失败");
			e.printStackTrace();
		}
		return obj;
	}

}
