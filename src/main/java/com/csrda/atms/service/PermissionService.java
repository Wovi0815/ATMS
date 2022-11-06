 
package com.csrda.atms.service;


import java.util.List;
import java.util.Map;

import com.csrda.atms.pojo.Permission;



/**  
* @author WangWei
* @description
* @Date 2022年9月10日 下午07:13:15
*/

public interface PermissionService {
    /**
     * 根据用户名Id查询权限
     * @param string 
     */
    List<Permission> findPermissionListByUserId(int userId);


}