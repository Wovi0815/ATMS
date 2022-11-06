 
package com.csrda.atms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
* @author WangWei
* @description
* @Date 2022年10月31日 下午9:18:19
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo {
    
    //过期时间
    private Long expireTime;
    
    //token
    private String token;

}
   