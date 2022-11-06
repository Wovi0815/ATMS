
package com.csrda.atms.pojo;

import java.io.Serializable;

import java.util.Date;



import lombok.Data;

/**  
* @author WangWei
* @description
* @Date 2022年8月10日 下午9:37:32
*/

@Data
public class Role implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色编号
     */
    private int id;

    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    
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
    

   
   
    
    
}
   