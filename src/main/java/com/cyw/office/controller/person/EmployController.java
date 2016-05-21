package com.cyw.office.controller.person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cyw.office.entity.person.Employee;
import com.cyw.office.service.person.IEmpService;
import com.cyw.office.util.DateUtil;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/person")
public class EmployController {
	@Autowired
	private IEmpService empService;
	/**
	 * 跳转员工管理首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/employee.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("person/employ");
	}

	/**
	 * 获取员工信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getEmpPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getEmpPage(HttpServletRequest request,
			HttpServletResponse response) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		PageBean pageBean = new PageBean(Integer.parseInt(page!=null&&page!=""?page:"0"),
				Integer.parseInt(rows!=null&&rows!=""?rows:"0"));
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("start", pageBean.getStart());
		paramsMap.put("end", pageBean.getEnd());
		paramsMap.put("sort", sort);
		paramsMap.put("order", order);
		List<Employee> empList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			empList = empService.getEmpPage(paramsMap);
			if (empList != null && empList.size() != 0) {
				for (Employee emp : empList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", emp.getId());
					objInfo.put("emp_code", emp.getEmpCode());
					objInfo.put("emp_name", emp.getEmpName());
					objInfo.put("emp_sex", emp.getEmpSex());
					objInfo.put("dept_code", emp.getDeptCode());
					objInfo.put("dept_name", emp.getDeptName());
					objInfo.put("posi_code", emp.getPosiCode());
					objInfo.put("posi_name", emp.getPosiName());
					objInfo.put("entrytime", DateUtil.format(emp.getEntrytime()));
					objInfo.put("birthday", DateUtil.format(emp.getBirthday()));
					objInfo.put("email", emp.getEmail()!=null&&emp.getEmail()!=""?emp.getEmail():"");
					objInfo.put("address", emp.getAddress()!=null&&emp.getAddress()!=""? emp.getAddress():"");
					objInfo.put("telphone", emp.getTelphone()!=null?emp.getTelphone():"");
					objInfo.put("emp_image", emp.getEmpImage()!=null?emp.getEmpImage():"");
					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total", empList.get(0).getTotalCount());
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
	 * 添加员工信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addEmploy.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addEmploy(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		String empCode = request.getParameter("empCode");
		String empName = request.getParameter("empName");
		String empSex = request.getParameter("empSex");
		String deptCode = request.getParameter("deptName");
		String posiCode = request.getParameter("posiName");
		String telphone = request.getParameter("telphone");
		String birthday = request.getParameter("birthday");
		String entryTime = request.getParameter("entryTime");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("empImagefile");
		String downloadPath = request.getSession().getServletContext()
				.getRealPath("/").replace("\\", "/")
				+ "/imageEmp/";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("empCode",empCode);
		paramsMap.put("empName",empName);
		paramsMap.put("deptCode",deptCode);
		paramsMap.put("posiCode",posiCode);
		paramsMap.put("empSex",empSex);
		paramsMap.put("telphone",telphone);
		paramsMap.put("birthday",birthday);
		paramsMap.put("entryTime",entryTime);
		paramsMap.put("email",email);
		paramsMap.put("address",address);	
		try{
			//查重复
			Employee emp = empService.findEmpByCode(empCode);
			if(emp!=null){
				obj.put("error", "1");
				obj.put("errorMsg", "员工编号重复");
			}else{
				if(!file.isEmpty()){
					String path = "emp_"+empCode+"."+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1);
					File f = new File(downloadPath+path);
					InputStream in = file.getInputStream();
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					in.close();
					FileOutputStream out = new FileOutputStream(f);
					out.write(bytes);
					out.flush();
					out.close();
					paramsMap.put("empImage",path);	
				}
				 empService.insert(paramsMap);
				 obj.put("error", "0");
			}
		} catch (FileNotFoundException e) {
			obj.put("error", "1");
			obj.put("errorMsg", "文件上传失败");
			e.printStackTrace();
		} catch (IOException e) {
			obj.put("error", "1");
			obj.put("errorMsg", "文件上传失败");
			e.printStackTrace();
		}catch(Exception e){
			obj.put("error", "1");
			obj.put("errorMsg", "添加失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 修改信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateEmploy.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateEmploy(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		String empCode = request.getParameter("empCode");
		String empName = request.getParameter("empName");
		String empSex = request.getParameter("empSex");
		String deptCode = request.getParameter("deptName");
		String posiCode = request.getParameter("posiName");
		String telphone = request.getParameter("telphone");
		String birthday = request.getParameter("birthday");
		String entryTime = request.getParameter("entryTime");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("empImagefile_edit");
		String downloadPath = request.getSession().getServletContext()
				.getRealPath("/").replace("\\", "/")
				+ "/imageEmp/";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("empCode",empCode);
		paramsMap.put("empName",empName);
		paramsMap.put("deptCode",deptCode);
		paramsMap.put("posiCode",posiCode);
		paramsMap.put("empSex",empSex);
		paramsMap.put("telphone",telphone);
		paramsMap.put("birthday",birthday);
		paramsMap.put("entryTime",entryTime);
		paramsMap.put("email",email);
		paramsMap.put("address",address);	
		try{
			if(!file.isEmpty()){
				//替换或添加图片
				String path = "emp_"+empCode+"."+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1);
				File f = new File(downloadPath+path);
				InputStream in = file.getInputStream();
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				in.close();
				FileOutputStream out = new FileOutputStream(f);
				out.write(bytes);
				out.flush();
				out.close();
				paramsMap.put("empImage",path);	
			}
			 empService.updateEmploy(paramsMap);
			 obj.put("error", "0");
			
		} catch (FileNotFoundException e) {
			obj.put("error", "1");
			obj.put("errorMsg", "文件上传失败");
			e.printStackTrace();
		} catch (IOException e) {
			obj.put("error", "1");
			obj.put("errorMsg", "文件上传失败");
			e.printStackTrace();
		}catch(Exception e){
			obj.put("error", "1");
			obj.put("errorMsg", "修改失败");
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 删除员工
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteEmploy.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteEmploy(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String empImages = request.getParameter("empImages");
		String[] empCodes = ids.split(","); 
		String[] paths = empImages.split(",");
		String downloadPath = request.getSession().getServletContext()
				.getRealPath("/").replace("\\", "/")
				+ "/imageEmp/";
		try{
			int result=empService.deleteEmploy(empCodes);
			if(result!=0){
				obj.put("error",'0');
				//删除对应 
				for(int i = 0;i<paths.length;i++){
					if(!paths[i].isEmpty()){
						File file = new File(downloadPath+paths[i]);
						if(file.exists()){
							file.delete();
						}
					}
				}
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
