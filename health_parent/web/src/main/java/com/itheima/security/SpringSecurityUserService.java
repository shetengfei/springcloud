package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/19
 * @description ：
 * @version: 1.0
 */
public class SpringSecurityUserService implements UserDetailsService {

    //远程调用service获取用户信息
    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByUsername(username);

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        if (sysUser != null) {
            Set<Role> roleSet = sysUser.getRoles();
                for (Role role : roleSet) {
                    Set<Permission> permissionSet = role.getPermissions();
                        for (Permission permission : permissionSet) {
                            String keyword = permission.getKeyword();
                            grantedAuthorityList.add(new SimpleGrantedAuthority(keyword));
                        }

                }

        }
        UserDetails userDetails = new User(username,sysUser.getPassword(),grantedAuthorityList);
        return userDetails;
    }
}
