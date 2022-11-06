 
package com.csrda.atms.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csrda.atms.dao.UserDao;
import com.csrda.atms.pojo.User;



/**  
* @author WangWei
* @param <T>
* @description
* @Date 2022年8月10日 下午10:13:35
*/
@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;


    public User qryUserByUserName(String username) {
         
        return userDao.qryUserByUserName(username);
    }
    
    
    public List<Map<String,Object>> qryAllUser() {
        
        return userDao.qryAllUser();
    }


    
}