package com.cyw.office.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyw.office.entity.sys.Resource;
import com.cyw.office.service.IResService;

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
}
