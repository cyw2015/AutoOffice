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

import com.cyw.office.entity.document.Approval;
import com.cyw.office.service.document.IApprovalService;
import com.cyw.office.util.CommonUtil;
import com.cyw.office.util.DateUtil;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/document")
public class ApprovalController {

	@Autowired
	private IApprovalService apprService;
	/**
	 * 公文审批
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/approval.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("document/approval");
	}
	
	/**
	 * 获取审批信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getApprovalPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getApprovalPage(HttpServletRequest request,
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
		List<Approval> apprList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			apprList = apprService.getApprovalPage(paramsMap);
			if (apprList != null && apprList.size() != 0) {
				for (Approval appr : apprList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", appr.getId());
					objInfo.put("docCode", appr.getDocCode());
					objInfo.put("docTitle", appr.getDocTitle());
					objInfo.put("editTime", DateUtil.format(appr.getEditTime()));
					objInfo.put("creater", appr.getCreater());
					objInfo.put("createrName", appr.getCreaterName());
					objInfo.put("createrDept", appr.getCreaterDept());
					objInfo.put("approver", appr.getApprover());
					objInfo.put("approverName", appr.getApproverName());
					objInfo.put("approverDept", appr.getApproverDept());
					objInfo.put("apprDate", DateUtil.format(appr.getApprDate()));
					objInfo.put("apprAdvice", appr.getApprAdvice());
					objInfo.put("apprState", appr.getApprState());
					arr.add(objInfo);
				}
				obj.put("rows", arr);
				obj.put("total", apprList.get(0).getTotalCount());
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
	 * 审批
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateDocAppr.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDocAppr(HttpServletRequest request,
			HttpServletResponse response) {
		String docCode = request.getParameter("docCode");
		String isPass = request.getParameter("isPass");
		String apprAdvice = request.getParameter("apprAdvice");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("docCode", docCode);
		paramsMap.put("state",isPass!=""&&isPass.equals("1")?"1":"-1");
		paramsMap.put("docState",isPass!=""&&isPass.equals("1")?"2":"-1");
		paramsMap.put("pubTime",isPass!=""&&isPass.equals("1")?"sysdate":"null");
		paramsMap.put("apprAdvice", apprAdvice);
		paramsMap.put("approver",CommonUtil.getLoginUserName());
		JSONObject obj = new JSONObject();
		try {
			int i = apprService.updateDocAppr(paramsMap);
			if(i!=0){
				obj.put("error", "0");
			}else{
				obj.put("error", "1");
				obj.put("errorMsg", "审核失败");
			}
		} catch (Exception e) {
			obj.put("error", "1");
			obj.put("errorMsg", "审核失败");
			e.printStackTrace();
		}
		return obj;
	}
}
