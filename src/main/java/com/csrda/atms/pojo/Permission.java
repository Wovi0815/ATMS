
package com.csrda.atms.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**  
* @author WangWei
* @description
* @Date 2022年8月10日 下午9:37:32
*/

@Data
public class Permission implements Serializable{
    
    private static final long serialVersionUID = 1L;

    
    /**
     * 权限编号
     */
    private int id;

    /**
     * 权限编码
     */
    private String permitCode;
    /**
     * 权限名称
     */
    private String permitName;
    /**
     * 父权限编号
     */
    private int parentId;
    /**
     * 父权限名称
     */
    private String parentName;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 路由名称
     */
    private String pathName;
    /**
     * 授权路径
     */
    private String url;
    /**
     * 权限类型（0-目录 1-菜单 2-按钮）
     */
    private int type;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private int orderNum;
    
    /**
     * 创建人
     */
    private int creator;
    
    /**
     * 创建时间
     */
    private Date create_time;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 更新人
     */   
    private String update_by;
    /**
     * 禁用 1可用，0禁用
     */   
    private String is_delete;
    
    
    /**
     * 子菜单列表
     */
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List <Permission> children = new ArrayList<Permission>();
    
    
    /**
     * 用于前端判断是菜单目录或按钮
     */
    
    private String value;
    
    
    /**
     * 是否展开
     */
    
    private Boolean open;
  
}
   