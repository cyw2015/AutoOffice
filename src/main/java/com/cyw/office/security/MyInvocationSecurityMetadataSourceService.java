package com.cyw.office.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问
 * 
 * @author cyw
 * 
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	// @Autowired
	// private AuthorityResMapper authorityResMapper;
	// @Autowired
	// private AuthorityMapper authorityMapper;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	// private RequestMatcher requestMatcher = new AntPathRequestMatcher();

	// tomcat服务启动时实例化一次
	public MyInvocationSecurityMetadataSourceService() {
		loadResourceDefine();
	}

	// tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系
	private void loadResourceDefine() {
		// List<String> query = authorityMapper.selectAuthority();
		// 此处select authority_code from t_authorities
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ROLE_USER");
		atts.add(ca);
		resourceMap.put("/index.jsp", atts);
		//for (String auth : query) {
			//ConfigAttribute ca = new SecurityConfig(auth);// "ROLE_ADMIN"
			//List<String> query1 = authorityResMapper.selectResByAuth(auth);
			// select t.resource_name from t_resources t ,
			// t_authorities_resources ar,t_authorities a
			// where t.resource_code = ar.resource_code and ar.authority_code =
			// a.authority_code and a.authority_name=

			//for (String res : query1) {
				//String url = res;
				// 判断资源文件和权限的对应关系，如果已经存在，要进行增加
				//if (resourceMap.containsKey(url)) {
					//Collection<ConfigAttribute> value = resourceMap.get(url);
					//value.add(ca);
					//resourceMap.put(url, value);
					// "log.jsp","role_user,role_admin"
				//} else {
					//atts.add(ca);
					//resourceMap.put(url, atts);
				//}
				//resourceMap.put(url, atts);
			//}
		//}
	}

	// 参数是要访问的url，返回这个url对于的所有权限（或角色）
	public Collection<ConfigAttribute> getAttributes(Object obj)
			throws IllegalArgumentException {
		// 将参数转为url
		String url = ((FilterInvocation) obj).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			//RequestMatcher requestMatcher = new AntPathRequestMatcher(url,resURL);
			if(urlMatcher.pathMatchesUrl(resURL, url)){
				//if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
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
