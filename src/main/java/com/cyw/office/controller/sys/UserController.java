package com.cyw.office.controller.sys;

import java.text.SimpleDateFormat;
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
import com.cyw.office.service.IUserService;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/sys")
public class UserController {

	@Autowired
	private IUserService userService;
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

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
								fmt.format(user.getCreatedate()));
					if (user.getDeadline() != null)
						objInfo.put("deadline", fmt.format(user.getDeadline()));
					if (user.getLastLogin() != null)
						objInfo.put("last_login",
								fmt.format(user.getLastLogin()));
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
}
