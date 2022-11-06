 
package com.csrda.atms.security;

import org.springframework.security.core.AuthenticationException;

/**  
* @author WangWei
* @description
* @Date 2022年10月14日 下午11:42:07
*/

public class CustomerAuthenticationException extends AuthenticationException{
    public CustomerAuthenticationException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

  
}
   