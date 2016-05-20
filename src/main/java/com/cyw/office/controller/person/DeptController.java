package com.cyw.office.controller.person;

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

import com.cyw.office.entity.person.Department;
import com.cyw.office.service.person.IDeptService;

/**
 * 部门管理控制器
 * @author cyw
 *
 */
@Controller
@RequestMapping(value = "/person")
public class DeptController {
	
	@Autowired
	private IDeptService deptService;
	/**
	 * 跳转部门管理首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/department.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("person/dept");
	}
	
	/**
	 * 获取部门信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDeptPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getDeptPage(HttpServletRequest request,
			HttpServletResponse response) {
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		String deptCode = request.getParameter("id");
		if (deptCode == null) {
			deptCode = "0";
		}
		List<Department> deptList= null;
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("sort", sort);
		paramsMap.put("order", order);
		paramsMap.put("deptCode", deptCode);
		JSONArray arr = new JSONArray();
		try{
			deptList = deptService.getDeptPage(paramsMap);
			if(deptList!=null &&deptList.size()!=0){
				for (Department dept : deptList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", dept.getId());
					objInfo.put("dept_code", dept.getDeptCode());
					objInfo.put("dept_name", dept.getDeptName());
					objInfo.put("dept_desc", dept.getDeptDesc());
					objInfo.put("super_dept", dept.getSuperDept());
					objInfo.put("state", dept.getState());
					objInfo.put("total", dept.getTotalCount());
					arr.add(objInfo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return arr;
	}
	
	/**
	 * 获取部门combotree
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDeptTree.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getDeptTree(HttpServletRequest request,
			HttpServletResponse response) {
		String deptCode = request.getParameter("id");
		if (deptCode == null) {
			deptCode = "0";
		}
		List<Department> deptList= null;
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("deptCode", deptCode);
		JSONArray arr = new JSONArray();
		try{
			deptList = deptService.getDeptPage(paramsMap);
			if(deptList!=null &&deptList.size()!=0){
				for (Department dept : deptList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id",dept.getDeptCode());
					objInfo.put("text", dept.getDeptName());
					objInfo.put("state", dept.getState());
					arr.add(objInfo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return arr;
	}

	@RequestMapping(value = "/addDept.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addDept(HttpServletRequest request,HttpServletResponse response){
		String deptCode = request.getParameter("deptCode");
		String deptName = request.getParameter("deptName");
		String superDept = request.getParameter("superDept");
		String deptDesc = request.getParameter("deptDesc");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("deptCode", deptCode);
		paramsMap.put("deptName",deptName);
		paramsMap.put("superDept", superDept);
		paramsMap.put("deptDesc", deptDesc);
		JSONObject obj = new JSONObject();
		try{
			//查询账号是否存在
			Department dept = deptService.findDeptByDeptCode(deptCode);
			if(dept!=null){
				obj.put("error",'1');
				obj.put("errorMsg","编号已存在");
				return obj;
			}else{
				deptService.insert(paramsMap);
				obj.put("error",'0');
			}
			//插入
		}catch(Exception e){
			obj.put("error",'1');
			obj.put("errorMsg","新增失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 删除部门
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteDept.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteDept(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String[] deptCodes = ids.split(","); 
		try{
			int result=deptService.deleteDeptsByCode(deptCodes);
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
	 * 修改部门信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateDept.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDept(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String deptCode = request.getParameter("deptCode");
		String deptName = request.getParameter("deptName");
		String superDept = request.getParameter("superDept");
		String deptDesc = request.getParameter("deptDesc");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("deptCode", deptCode);
		paramsMap.put("deptName",deptName);
		paramsMap.put("superDept", superDept);
		paramsMap.put("deptDesc", deptDesc);
		try{
			deptService.updateDept(paramsMap);
			obj.put("error",paramsMap.get("error"));
			obj.put("errorMsg",paramsMap.get("errorMsg"));
		}catch(Exception e){
			obj.put("error",'1');
			obj.put("errorMsg","修改失败");
			e.printStackTrace();
		}
		return obj;
	}
}
