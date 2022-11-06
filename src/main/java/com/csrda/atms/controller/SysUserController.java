 
package com.csrda.atms.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csrda.atms.pojo.Permission;
import com.csrda.atms.pojo.RouterVo;
import com.csrda.atms.pojo.TokenVo;
import com.csrda.atms.pojo.User;
import com.csrda.atms.pojo.UserInfo;
import com.csrda.atms.utils.JwtUtils;
import com.csrda.atms.utils.MenuTree;
import com.csrda.atms.utils.RedisService;
import com.csrda.atms.utils.Result;

import io.jsonwebtoken.Jwts;

/**  
* @author WangWei
* @description
* @Date 2022年10月31日 下午9:28:55
*/
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {
    @Resource 
    private RedisService redisService;
    @Resource 
    private JwtUtils jwtUtils;
    
    /**
     * 刷新token
     */
    @PostMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
       //从header中获取前端提交的token
        String token = request.getHeader("token");
       // 如果header中没有token，则从参数中获取
        
       if(ObjectUtils.isEmpty(token)) {
           token  = request.getParameter("token");
       }
       //从SpringSecurity 上下文获取用户信息 
        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();
       //获取身份信息
        UserDetails details  =  (UserDetails)authentication.getPrincipal();
        
       //重新生成token
        String reToken = "";
       //验证原来的token是否合法
        if(jwtUtils.validateToken(token, details)) {
           //生成新的token
            reToken = jwtUtils.refreshToken(token);
       }
       
       //获取本次token到期时间，交给前端判断
        long expireTime = Jwts.parser().setSigningKey(jwtUtils.getSecret())
            .parseClaimsJws(reToken.replace("jwt_", ""))
            .getBody().getExpiration().getTime();
        String oldTokenKey = "token_"+token;
        redisService.del(oldTokenKey);
        
        //储存新的token
         String newTokenKey = "token_"+reToken;
         redisService.set(newTokenKey,reToken,jwtUtils.getExpiration()/1000);
        //创建TokenVo对象
         TokenVo tokenVo = new TokenVo (expireTime,reToken);
        //返回数据
         
        return Result.ok(tokenVo).message("token生成成功");
        
    }
    /**
     * 获取用户信息
     */
    
    @GetMapping("/getInfo")
    public Result getInfo() {
      //从SpringSecurity 上下文获取用户信息 
        Authentication authentication =  
            SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return Result.error().message("用户信息查询失败");
        }
       //获取用户信息
        User user = (User)authentication.getPrincipal();
       //用户权限集合
        List<Permission>permissionList = user.getPermissionList();
        Object[] roles = permissionList.stream()
            .filter(Objects::nonNull)
            .map(Permission::getPermitCode).toArray();
       //创建用户信息对象
        UserInfo userInfo = new UserInfo(user.getId(),roles);
        
        return Result.ok(userInfo).message("用户信息查询成功");
    }
    
    /**
     * 获取登录用户的菜单数据。，，
     */
    
    @GetMapping("/getMenuList")
    public Result getMenuList() {
        //从SpringSecurity 上下文获取用户信息 
        Authentication authentication =  
            SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        User user = (User)authentication.getPrincipal(); 
        //用户权限集合
        List<Permission>permissionList = user.getPermissionList();
        System.out.println(user);
        //筛选当前用户拥有的菜单和目录
        List<Permission> collect = permissionList.stream()
            .filter(item->item!=null && item.getType()!=2) //2表示按钮
            .collect(Collectors.toList());
       //生成路由数据
        List<RouterVo> routerVoList =MenuTree.makeRouter(collect, 0);
       //访问数据
        return Result.ok(routerVoList).message("菜单数据成功");
        
    }
}
   