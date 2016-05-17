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

import com.cyw.office.entity.sys.Resource;
import com.cyw.office.service.IResService;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/sys")
public class ResController {
	@Autowired
	private IResService resService;

	@RequestMapping(value = "/ResTree.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray resTree(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		if (id == null) {
			id = "0";
		}
		List<Resource> resList= null;
//		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
		resList = resService.findResTree(id);
		if(resList!=null&&resList.size()!=0){
			for(Resource res:resList){
				JSONObject objInfo = new JSONObject();
				objInfo.put("id", res.getId());
				objInfo.put("text", res.getResourceName());
				objInfo.put("state", res.getState());
				objInfo.put("iconCls", res.getIconcls());
				objInfo.put("url", res.getUrl());
				objInfo.put("nid", res.getParent());
				arr.add(objInfo);
			}
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

	@RequestMapping("/resources.do")
	public String toIndex(HttpServletRequest request, Model model) {
		return "sys/resource";
	}
	
	@RequestMapping(value = "/getResPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getResPage(HttpServletRequest request,
			HttpServletResponse response) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("start", pageBean.getStart());
		paramsMap.put("end", pageBean.getEnd());
		paramsMap.put("sort", sort);
		paramsMap.put("order", order);
		List<Resource> resList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try{
			resList =resService.getResPage(paramsMap);
			if(resList!=null&&resList.size()!=0){
				for (Resource res : resList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", res.getId());
					objInfo.put("resourceCode", res.getResourceCode());
					objInfo.put("resourceName", res.getResourceName());
					objInfo.put("resourceDesc", res.getResourceDesc());
					objInfo.put("state", res.getState());
					objInfo.put("type", res.getType());
					objInfo.put("iconcls", res.getIconcls());
					objInfo.put("url", res.getUrl());
					objInfo.put("parent", res.getParent());
					objInfo.put("remark", res.getRemark());		
					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total",resList.get(0).getTotalCount());
			}else {
				obj.put("rows", "");
				obj.put("total",0);
			}
			
		}catch(Exception e){
			obj.put("rows", "");
			obj.put("total",0);
			e.printStackTrace();
		}
		
		return obj;
	}
}
