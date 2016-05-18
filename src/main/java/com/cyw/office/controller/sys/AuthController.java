package com.cyw.office.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyw.office.entity.sys.Authority;
import com.cyw.office.service.sys.IAuthService;
import com.cyw.office.util.CommonUtil;
import com.cyw.office.util.DateUtil;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/sys")
public class AuthController {
	@Autowired
	private IAuthService authService;
	
	@RequestMapping("/auths.do")
	public String toIndex(HttpServletRequest request, Model model) {
		return "sys/auth";
	}
	
	/**
	 * 获取权限信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAuthPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAuthPage(HttpServletRequest request,
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
		List<Authority> authList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			authList = authService.getAuthPage(paramsMap);
			if (authList != null && authList.size() != 0) {
				for (Authority auth : authList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", auth.getId());
					objInfo.put("authority_code", auth.getAuthorityCode());
					objInfo.put("authority_name", auth.getAuthorityName());
					objInfo.put("creater", auth.getCreater());
					objInfo.put("createDate",DateUtil.format(auth.getCreatedate()));
					objInfo.put("remark", auth.getRemark());
					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total", authList.get(0).getTotalCount());
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
	 * 增加权限信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addAuth.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addAuth(HttpServletRequest request,HttpServletResponse response){
		String authCode = request.getParameter("authCode");
		String authName = request.getParameter("authName");
		String remark = request.getParameter("remark");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("authCode", authCode);
		paramsMap.put("authName",authName);
		paramsMap.put("remark", remark);
		//获取当前登录用户
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		JSONObject obj = new JSONObject();
		try{
			//查询角色是否存在
			Authority auth = authService.findByAuthCode(authCode);
			if(auth!=null){
				obj.put("error",'1');
				obj.put("errorMsg","权限编码已存在");
				return obj;
			}else{
				int i = authService.insert(paramsMap);
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
	 * 修改权限信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateAuth.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateAuth(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String authCode = request.getParameter("authCode");
		String authName = request.getParameter("authName");
		String remark = request.getParameter("remark");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("authCode", authCode);
		paramsMap.put("authName",authName);
		paramsMap.put("remark", remark);
		try{
			int result=authService.updateByAuthCode(paramsMap);
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
	 * 删除权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteAuths.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteAuths(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String[] authCodes = ids.split(","); 
		try{
			int result= authService.deleteAuthsByIds(authCodes);
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
}
