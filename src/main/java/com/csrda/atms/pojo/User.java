
package com.csrda.atms.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**  
* @author WangWei
* @description
* @Date 2022年8月10日 下午9:37:32
*/

@Data
public class User implements Serializable, UserDetails{
    
    private static final long serialVersionUID = 1L;
    
    
    private int id;
    private String username;
    private String password;
    private String creator;
    private Date create_time;
    private String update_by;
    private Date update_time;
   

    /**
     * 账户是否过期 1未过期，0过期
     */
    private boolean isAccountNonExpired = true;
    /**
     * 账户是否锁定 1未锁定，0锁定
     */
    private boolean isAccountNonLocked = true;
    /**
     * 密码是否过期 1未过期，0过期
     */
    private boolean isCredentialsNonExpired = true;
    /**
     * 账户是否可用 1可用，0删除用户
     */
    private boolean isEnabled = true;
    
   
    /**
     * 权限列表
     */
    
    Collection<? extends GrantedAuthority> authorities;  
    
    /**
     * 用户权限列表
     */
    List<Permission> permissionList;
    
 

   
   
    
    
}
   