 
package com.csrda.atms.service;


import java.util.List;
import java.util.Map;

import com.csrda.atms.pojo.User;


/**  
* @author WangWei
* @description
* @Date 2022年8月10日 下午10:13:15
*/

public interface UserService {
    /**
     * 根据用户名查询用户信息
     */
    User qryUserByUserName(String username);
    /**
     * 查询所有用户
     */
    List<Map<String,Object>> qryAllUser();

}
   