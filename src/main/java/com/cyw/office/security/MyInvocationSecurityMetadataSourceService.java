package com.cyw.office.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.cyw.office.entity.sys.Resource;
import com.cyw.office.service.sys.IResService;

/**
 * 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问
 * 
 * @author cyw
 * 
 */
@Service("mySecurityMetadataSource")
public class MyInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {
	private IResService resService;
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	// tomcat服务启动时实例化一次
	public MyInvocationSecurityMetadataSourceService() {
		loadResourceDefine();
	}
	public MyInvocationSecurityMetadataSourceService(IResService resService) {
		this.resService=resService;
		loadResourceDefine();
	}

	// tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系
	// 我直接处理资源编码和url关系
	private void loadResourceDefine() {
		List<Resource> query = resService.findAllRes();
		System.out.println("size="+query.size());
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		ConfigAttribute cab = new SecurityConfig("RES_SYS_MAIN");
		atts.add(cab);
		resourceMap.put("/index.jsp", atts);
		resourceMap.put("/**", atts);
		for (Resource auth : query) {
			ConfigAttribute ca = new SecurityConfig(auth.getResourceCode());// "ROLE_ADMIN"
			String url = auth.getUrl();
			if(url!=null)
			// 判断资源文件和权限的对应关系，如果已经存在，要进行增加
			if (resourceMap.containsKey(url)) {
				Collection<ConfigAttribute> value = resourceMap.get(url);
				value.add(ca);
				resourceMap.put(url, value);
			} else {
				atts.add(ca);
				resourceMap.put(url, atts);
			}
			resourceMap.put(url, atts);
			 }
	}

	// 参数是要访问的url，返回这个url对于的所有权限（或角色）
	public Collection<ConfigAttribute> getAttributes(Object obj)
			throws IllegalArgumentException {
		// 将参数转为url
		String url = ((FilterInvocation) obj).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if(resURL!=null)
			if (urlMatcher.pathMatchesUrl(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	public boolean supports(Class<?> class1) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

}
