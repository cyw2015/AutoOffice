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

import com.cyw.office.entity.person.Position;
import com.cyw.office.service.person.IPosiService;
import com.cyw.office.util.PageBean;

/**
 * 岗位管理
 * @author cyw
 *
 */
@Controller
@RequestMapping(value = "/person")
public class PosiController {
	
	@Autowired
	private IPosiService posiService;
	/**
	 * 跳转部门管理首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/position.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("person/position");
	}
	
	/**
	 * 获取岗位信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPosiPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getPosiPage(HttpServletRequest request,
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
		List<Position> posiList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			posiList = posiService.getPosiPage(paramsMap);
			if (posiList != null && posiList.size() != 0) {
				for (Position posi : posiList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", posi.getId());
					objInfo.put("posi_name", posi.getPosiName());
					objInfo.put("posi_desc", posi.getPosiDesc());
					objInfo.put("posi_level", posi.getPosiLevel());
					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total", posiList.get(0).getTotalCount());
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
	 * 添加职位
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addPosi.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addPosi(HttpServletRequest request,HttpServletResponse response){
		String posiName = request.getParameter("posiName");
		String posiLevel = request.getParameter("posiLevel");
		String posiDesc = request.getParameter("posiDesc");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("posiName", posiName);
		paramsMap.put("posiLevel",posiLevel);
		paramsMap.put("posiDesc", posiDesc);
		JSONObject obj = new JSONObject();
		try{
			//查询账号是否存在
			Position posi = posiService.findPosiByName(posiName);
			if(posi!=null){
				obj.put("error",'1');
				obj.put("errorMsg","职位已存在");
				return obj;
			}else{
				posiService.insert(paramsMap);
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
	 * 修改职位信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updatePosi.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updatePosi(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String oldPosName = request.getParameter("oldPosName");
		String posiName = request.getParameter("posiName");
		String posiLevel = request.getParameter("posiLevel");
		String posiDesc = request.getParameter("posiDesc");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", id);
		paramsMap.put("posiName", posiName);
		paramsMap.put("posiLevel",posiLevel);
		paramsMap.put("posiDesc", posiDesc);
		JSONObject obj = new JSONObject();
		try{
			if(!oldPosName.equals(posiName)){
				//查询账号是否存在
				Position posi = posiService.findPosiByName(posiName);
				if(posi!=null){
					obj.put("error",'1');
					obj.put("errorMsg","职位已存在");
					return obj;
				}
			}
			int i = posiService.updatePosi(paramsMap);
			if(i!=0){
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
	 * 删除职位
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deletePosi.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deletePosi(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String id = request.getParameter("ids");
		String[] ids = id.split(","); 
		try{
			int result=posiService.deletePosiById(ids);
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
