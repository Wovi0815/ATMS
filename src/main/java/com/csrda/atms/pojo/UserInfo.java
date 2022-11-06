 
package com.csrda.atms.pojo;

import java.io.Serializable;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
* @author WangWei
* @description
* @Date 2022年10月31日 下午10:09:22
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable{
    private int id;//用户id
    private Object[] roles;//角色权限集合
}
   