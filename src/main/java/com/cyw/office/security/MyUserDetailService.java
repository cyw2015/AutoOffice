package com.cyw.office.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cyw.office.entity.sys.Resource;
import com.cyw.office.entity.sys.User;
import com.cyw.office.service.sys.IResService;
import com.cyw.office.service.sys.IUserService;

/**
 * 从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期
 * 
 * @author cyw
 * 
 */
@Service
public class MyUserDetailService implements UserDetailsService {
	 
	 @Autowired
	 private IUserService userService;
	 @Autowired
	 private IResService resService;
	 
	// 登陆验证时，通过username获取用户的所有权限信息，
	// 并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
	public UserDetails loadUserByUsername(String usercode)
			throws UsernameNotFoundException {
		
		// 使用User服务类查询数据用户是否存在,如不存在或密码错误则抛出对应的异常  
        User user = this.userService.findByUserCode(usercode);  
        if (null == user)  
            throw new UsernameNotFoundException("用户/密码错误,请重新输入!");  
        System.out.println(user.getUserCode()+"|"+user.getUserPassword());
        
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		// 取得用户权限
		List<Resource> ress = resService.findResByUserCode(usercode);
	     System.out.println("size="+ress.size());
		 if (null == ress || ress.isEmpty())  
	            throw new UsernameNotFoundException("权限不足!");  
		// 把权限赋值给当前对象  
		 else{
			 for(Resource res : ress){
				 System.out.println(res.getResourceCode()+"|"+res.getResourceName());
				 auths.add(new SimpleGrantedAuthority(res.getResourceCode()));
			 }
		 }
		org.springframework.security.core.userdetails.User userDetail= new org.springframework.security.core.userdetails.User(usercode, user.getUserPassword(), user.getEnabled()==1?true:false, true, true, true, auths);
		return userDetail;
	}

}
