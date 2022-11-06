 
package com.csrda.atms.utils;

import lombok.Data;

/**  
* @author WangWei
* @description
* @Date 2022年8月20日 下午11:26:30
*/
@Data
public class Result<T> {
    private Boolean success;//是否成功
    private Integer code;//状态码
    private String message;//返回消息
    private T data;//返回数据
   
   
    private Result() { } //私有构造函数，禁止在其他类创建对象
    
    /**
     * 成功返回，不返回数据
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(){
        Result<T> result =new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功");
        return result;
        
    }
    /**
     * 成功返回，并返回数据
     * @param <T>
     * @param data
     * @return
     */
    public static<T> Result<T> ok(T data){
        Result<T> result =new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功");
        result.setData(data);
        return result;
        
    }
    /**
     * 执行失败
     * @param <T>
     * @return
     */
    public static<T> Result<T> error(){
        Result<T> result =new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("执行失败");
        return result;
        
    }
    /**
     * 设置是否成功
     * @param success
     * @return
     */
    public Result<T> success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    /**
     * 设置状态码
     * @param code
     * @return
     */
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
    
    /**
     * 设置返回消息
     * @param message
     * @return
     */
    public Result<T> message(String message){
        this.setMessage(message);
        return this;
    }

    
    
    

    
    
        
    
}


   