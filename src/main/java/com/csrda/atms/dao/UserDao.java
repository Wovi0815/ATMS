 
package com.csrda.atms.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.csrda.atms.pojo.User;



/**  
* @author WangWei
* @description
* @Date 2022年8月10日 下午9:44:54
*/

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     */
    @Select("SELECT  *  " + 
            "FROM sys_user t  " + 
            "WHERE t.username=#{username} AND t.is_delete ='0'")
    User qryUserByUserName(String username);
    
    /**
     * 查询所有用户
     */
    @Select("SELECT  *  " + 
            "FROM sys_user t  " 
            )
    List<Map<String,Object>> qryAllUser();

}
   