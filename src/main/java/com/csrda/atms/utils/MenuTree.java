 
package com.csrda.atms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.csrda.atms.pojo.Permission;
import com.csrda.atms.pojo.RouterVo;

/**  
* @author WangWei
* @description
* @Date 2022年11月4日 下午11:03:34
*/
/**
 * 生成路由
 * 参数1：菜单列表
 * 参数2：父菜单id
 * @author WangWei
 *
 */
public class MenuTree {
    public static List<RouterVo> makeRouter(List<Permission>menuList,int pid){
        //创建集合保存路由信息
        List<RouterVo> routerList = new ArrayList<RouterVo>();
        //判断是否为空，如果不为空则使用菜单列表，否则创建集合对象。
        Optional.ofNullable(menuList).orElse(new ArrayList<Permission>())
            //筛选不为空的菜单与菜单父ID相同的数据
        .stream().filter(item->item!=null && item.getParentId() == pid)
        .forEach(item->{
            //创建路由信息对象
            RouterVo router = new RouterVo();
            router.setName(item.getPathName());//路由名称
            router.setPath(item.getPath());//路由地址
            //判断当前菜单是否是一级菜单
            if(item.getParentId() == 0) {
                router.setComponent("Layout");//一级菜单组件
                router.setAlwaysShow(true);//显示路由
            }else {
                router.setComponent(item.getUrl());//具体的某一个组件
                router.setAlwaysShow(false);//不显示路由
            }
            
            //设置Meta
            router.setMeta(router.new Meta(item.getPermitName(),
                                                item.getIcon(),
                                                item.getPermitCode().split(",")));
            
            //递归生成路由
            
            List<RouterVo> children = makeRouter(menuList,item.getId());
            router.setChilden(children);
            
            //将路由信息添加到集合
            
            routerList.add(router);
        });
        
        
        return routerList;
        
    }
    
    
    /**
     * 生成菜单树
     */
    
    public static List<Permission> makeMenuTree(List<Permission>menuList,int pid){
        //创建集合保存菜单
        List<Permission>permissionList = new ArrayList<Permission>();
        //判断menuList是否为空，如果不为空则使用菜单列表，否则创建集合对象。
        Optional.ofNullable(menuList).orElse(new ArrayList<Permission>())
        .stream().filter(item->item!=null && item.getParentId() == pid)
        .forEach(item->{
            //创建菜单权限对象
            Permission permission = new Permission();
            //复制属性
            BeanUtils.copyProperties(item, permission);
            //获取每一个item的下级菜单，递归生成菜单树
            List<Permission>children  =  makeMenuTree(menuList,item.getId());    
            //设置子菜单
            permission.setChildren(children);
            //将菜单添加到集合
            permissionList.add(permission);
        });
        return permissionList;
        
    }
}
   