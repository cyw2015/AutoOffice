package com.cyw.office.controller.document;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.cyw.office.entity.document.Document;
import com.cyw.office.service.document.IPublishService;
import com.cyw.office.util.CommonUtil;
import com.cyw.office.util.DateUtil;
import com.cyw.office.util.PageBean;

@Controller
@RequestMapping(value = "/document")
public class PublishController {
	
	@Autowired
	private IPublishService pubService;
	/**
	 * 公文发布首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/publish.do")
	public ModelAndView main(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("document/publish");
	}

	/**
	 * 获取公文信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDocPubPage.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getDocPubPage(HttpServletRequest request,
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
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		List<Document> docList = null;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			docList = pubService.getDocPubPage(paramsMap);
			if (docList != null && docList.size() != 0) {
				for (Document doc : docList) {
					JSONObject objInfo = new JSONObject();
					objInfo.put("id", doc.getId());
					objInfo.put("doc_code", doc.getDocCode());
					objInfo.put("doc_title", (String)doc.getDocTitle());
					objInfo.put("doc_content", (String)doc.getDocContent());
					objInfo.put("editTime", DateUtil.format(doc.getEdittime()));
					objInfo.put("pubTime",DateUtil.format( doc.getPubtime()));
					objInfo.put("recipients", (String)doc.getRecipients());
					objInfo.put("recipientsCode", (String)doc.getRecipientsCode());
					objInfo.put("state", doc.getState());
					objInfo.put("attachment", (String)doc.getAttachment());
					objInfo.put("attachmentPath", (String)doc.getAttachmentPath());
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
	
	/**
	 * 添加公文信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addDocPub.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addDocPub(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		String docCode = request.getParameter("docCode");
		String docTitle = request.getParameter("docTitle");
		String[] reci = request.getParameterValues("recipients");
		//最后一个为空值
		List<String> recipients=new ArrayList<String>();
		for(int i=0;i<reci.length;i++){
			if(!reci[i].isEmpty()){
				recipients.add(reci[i]);
			}
		}
		String docContent = request.getParameter("docContent");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		MultipartFile file = multipartRequest.getFile("attachment");
		String downloadPath = request.getSession().getServletContext()
				.getRealPath("/").replace("\\", "/")
				+ "/document/";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("docCode",docCode);
		paramsMap.put("docTitle",docTitle);
		paramsMap.put("docContent",docContent);
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		paramsMap.put("recipients", recipients);
		try{
			//查重复
			Document doc = pubService.findDocByCode(docCode);
			if(doc!=null){
				obj.put("error", "1");
				obj.put("errorMsg", "公文编号重复");
			}else{
				if(!file.isEmpty()){
					String fileName = file.getOriginalFilename();
					String path = CommonUtil.getLoginUserName()+"_"+System.nanoTime()+"_"+fileName;
					File f = new File(downloadPath+path);
					InputStream in = file.getInputStream();
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					in.close();
					FileOutputStream out = new FileOutputStream(f);
					out.write(bytes);
					out.flush();
					out.close();
					paramsMap.put("attachmentPath",path);	
					paramsMap.put("attachment",fileName);	
				}
				 pubService.insert(paramsMap);
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
	 * 删除未发布公文
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deletePubDoc.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deletePubDoc(HttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		String ids = request.getParameter("ids");
		String attachmentPaths = request.getParameter("attachmentPaths");
		String[] docCodes = ids.split(","); 
		String[] paths = attachmentPaths.split(",");
		String downloadPath = request.getSession().getServletContext()
				.getRealPath("/").replace("\\", "/")
				+ "/document/";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("docCode",docCodes);
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		try{
			int result=pubService.deleteDocByCode(paramsMap);
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
	
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downloadDoc.do")
	@ResponseBody
	public void downloadDoc(HttpServletRequest request,
			HttpServletResponse response) {
		String attachmentPath = request.getParameter("attachmentPath");
		String attachment = request.getParameter("attachment");
		response.setContentType("application/x-msdownload");

		try {
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(attachment.getBytes("GB2312"), "ISO-8859-1"));
			String downloadPath = request.getSession().getServletContext()
					.getRealPath("/").replace("\\", "/")
					+ "/document/";
			File f = new File(downloadPath + attachmentPath);
			// 读取文件
			InputStream is = new FileInputStream(f);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			is.close();
			OutputStream out = response.getOutputStream();
			out = new BufferedOutputStream(response.getOutputStream());
			out.write(bytes);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改公文信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateDocPub.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDocPub(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		String docCode = request.getParameter("docCode");
		String docTitle = request.getParameter("docTitle");
		String[] reci = request.getParameterValues("recipients");
		//最后一个为空值
		List<String> recipients=new ArrayList<String>();
		for(int i=0;i<reci.length;i++){
			if(!reci[i].isEmpty()){
				recipients.add(reci[i]);
			}
		}
		String docContent = request.getParameter("docContent");
		String oldAttachmentPath = request.getParameter("oldAttachmentPath");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		MultipartFile file = multipartRequest.getFile("attachment");
		String downloadPath = request.getSession().getServletContext()
				.getRealPath("/").replace("\\", "/")
				+ "/document/";
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("docCode",docCode);
		paramsMap.put("docTitle",docTitle);
		paramsMap.put("recipients",recipients);
		paramsMap.put("docContent",docContent);
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		try{
			if(!file.isEmpty()){
				//替换或添加文件
				String fileName = file.getOriginalFilename();
				String path = CommonUtil.getLoginUserName()+"_"+System.nanoTime()+"_"+fileName;
				File f = new File(downloadPath+path);
				InputStream in = file.getInputStream();
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				in.close();
				FileOutputStream out = new FileOutputStream(f);
				out.write(bytes);
				out.flush();
				out.close();
				paramsMap.put("attachmentPath",path);	
				paramsMap.put("attachment",fileName);
			}
			//更新
			 int i = pubService.updateDocPub(paramsMap);
			 if(i!=0){
				 obj.put("error", "0");
				 //删除原有文件
				 if(!oldAttachmentPath.isEmpty())
				 {
					 File delfile = new File(downloadPath+oldAttachmentPath);
						if(delfile.exists()){
							delfile.delete();
						}
				 }
			 }
			 else{
				 obj.put("error", "1");
				 obj.put("errorMsg", "修改失败");
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
			obj.put("errorMsg", "修改失败");
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 送审
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendAppr.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject sendAppr(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		String docCode = request.getParameter("docCode");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("docCode",docCode);
		paramsMap.put("creater",CommonUtil.getLoginUserName());
		try{
			int i = pubService.sendAppr(paramsMap);
			if(i!=0){
				obj.put("error", "0");
			}else{
				obj.put("error", "1");
				obj.put("errorMsg", "送审失败");
			}
		}catch (Exception e){
			obj.put("error", "1");
			obj.put("errorMsg", "送审失败");
			e.printStackTrace();
		}
		return obj;
	}
}
