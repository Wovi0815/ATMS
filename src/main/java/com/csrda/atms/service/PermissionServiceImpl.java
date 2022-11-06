 
package com.csrda.atms.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csrda.atms.dao.PermissionDao;
import com.csrda.atms.pojo.Permission;




/**  
* @author WangWei
* @description
* @Date 2022年9月10日 下午07:13:15
*/

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionDao permissionDao;



    @Override
    public List<Permission> findPermissionListByUserId(int userId) {

        return permissionDao.findPermissionListByUserId(userId);
    }



    
}