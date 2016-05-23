package com.cyw.office.controller.document;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/document")
public class DocumentController {

	/**
	 * 公文详情页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDocDetail.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object > model = new HashMap<String, Object>();
		model.put("test","是");
		return new ModelAndView("document/docDetail",model);
	}
}
