 
package com.csrda.atms.security;


import java.util.List;

import java.util.stream.Collectors;

import javax.annotation.Resource;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.csrda.atms.pojo.Permission;
import com.csrda.atms.pojo.User;
import com.csrda.atms.service.PermissionService;
import com.csrda.atms.service.UserService;


/**  
* @author WangWei
* @description
* @Date 2022年9月3日 下午6:40:49
*/
@Component
public class CustomerUserDetailService implements UserDetailsService{
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userService.qryUserByUserName(username);
        if(user==null) {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        //查询权限列表
        List<Permission> permissionList =permissionService.findPermissionListByUserId(user.getId());

        //获取权限编码
        List<String> collect = permissionList.stream()
            .filter(item->item!=null)
            .map(item->item.getPermitCode()).filter(item->item!=null)
            .collect(Collectors.toList());
        //转换成数组
        String[] strings = collect.toArray(new String[collect.size()]);
        //设置权限列表
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        user.setAuthorities(authorityList);
        //设置菜单列表
        user.setPermissionList(permissionList);
        
        return user;
    }

}
   