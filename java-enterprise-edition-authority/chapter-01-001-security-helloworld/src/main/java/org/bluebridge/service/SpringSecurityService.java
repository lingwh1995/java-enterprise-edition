package org.bluebridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
/*自定义用户*/
public class SpringSecurityService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //判断用户名
        if(StringUtils.isEmpty(s)||!("admin".equals(s))){
            throw new UsernameNotFoundException("不存在此用户！");
        }
        //密码加密
        String pw = passwordEncoder.encode("123456");
        //返回一个用户,以及权限或角色
        return new User(s,pw, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal"));
    }
}

