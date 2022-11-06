 
package com.csrda.atms.controller;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csrda.atms.pojo.Permission;
import com.csrda.atms.pojo.User;
import com.csrda.atms.service.PermissionService;
import com.csrda.atms.service.UserService;
import com.csrda.atms.utils.Result;

/**  
* @author WangWei
* @description
* @Date 2022年8月10日 下午9:43:32
*/
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    // 跳转到人员管理
    @GetMapping("/qryUserByUserName")
    public Result<User> qryUserByUserName(String username) {
        User data = userService.qryUserByUserName("zhaoliu");

        return Result.ok(data);
    }


    @GetMapping("/qryAllUser")
    public Result<List<Permission>> qryAllUser() {
       List<Permission> permissionList =permissionService.findPermissionListByUserId(1);
   
        return  Result.ok(permissionList);
    }

}
