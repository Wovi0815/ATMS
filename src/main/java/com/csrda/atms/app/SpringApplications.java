 
package com.csrda.atms.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**  
* @author WangWei
* @description
* @Date 2022年8月10日 下午8:42:03
*/



@SpringBootApplication(scanBasePackages="com.csrda.atms.*")
@MapperScan("com.csrda.atms.dao")
@EnableTransactionManagement
public class SpringApplications {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplications.class, args);
    }
}


   