 
package com.csrda.atms.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.csrda.atms.pojo.Permission;




/**  
* @author WangWei
* @description
* @Date 2022年9月24日 下午7:31:54
*/

public interface PermissionDao {

    /**
     * 根据用户列表查询权限列表
     * @return 
     */
    @Select("SELECT  p.*" + 
            "FROM sys_user u   " + 
            "LEFT JOIN sys_user_role ur on u.id = ur.user_id   " + 
            "LEFT JOIN sys_role r  on ur.role_id = r.id   " + 
            "LEFT JOIN sys_role_permission rp  on rp.role_id = r.id   " + 
            "LEFT JOIN sys_permission p  on rp.permission_id = p.id   " + 
            "WHERE u.id=#{userId} AND p.is_delete ='0'"+
            "ORDER BY p.id asc")

    List<Permission> findPermissionListByUserId(int userId);

}
   