package com.cyw.office.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期
 * 
 * @author cyw
 * 
 */
@Service
public class MyUserDetailService implements UserDetailsService {
	// @Autowired
	// private UserMapper userMapper;
	// 登陆验证时，通过username获取用户的所有权限信息，
	// 并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		// 取得用户权限
//		List<Authority> auth = userMapper.findAuthByUserName(username);
		GrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_ADMIN");
		GrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_USER");
//		String password = null;
		// 取得用户的密码
		// password = userMapper.findUserByname(username).get(0);
		
		if (username.equals("lcy")) {
			auths = new ArrayList<GrantedAuthority>();
			auths.add(auth1);
			auths.add(auth2);
		}
		
		User user = new User(username, "e290c5be371f47157fbf413e0080dc5d", true, true, true, true, auths);
		return user;
	}

}
