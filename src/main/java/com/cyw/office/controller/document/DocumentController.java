package com.cyw.office.controller.document;

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

import com.cyw.office.entity.document.Document;
import com.cyw.office.service.document.IDocumentService;
import com.cyw.office.util.CommonUtil;
import com.cyw.office.util.DateUtil;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/document")
public class DocumentController {
	@Autowired
	private IDocumentService docService;
	/**
	 * 公文详情页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDocDetail.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		String docCode = request.getParameter("docCode");
		String type = request.getParameter("type");
		Map<String,Object> map = docService.getDocDetail(docCode);
		map.put("type", type);
		return new ModelAndView("document/docDetail",map);
	}
	
	/**
	 * 查看公文
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewDoc.do")
	public ModelAndView viewDoc(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("document/viewDoc");
	}
	
	/**
	 * 获取可查看的公文信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getViewDocPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getViewDocPage(HttpServletRequest request,
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
		paramsMap.put("custom",CommonUtil.getLoginUserName());
		List<Document> docList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			docList = docService.getViewDocPage(paramsMap);
			if (docList != null && docList.size() != 0) {
				for (Document doc : docList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", doc.getId());
					objInfo.put("doc_code", doc.getDocCode());
					objInfo.put("doc_title", (String)doc.getDocTitle());
					objInfo.put("creater", doc.getCreater());
					objInfo.put("creater_name", doc.getCreaterName());
					objInfo.put("pubTime",DateUtil.format( doc.getPubtime()));
					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total", docList.get(0).getTotalCount());
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
