package com.java.springboot.exception.exception;

/**
 * @Description: 自定义异常处理
 * @Author: zhangyadong
 * @Date: 2021/1/14 15:02
 * @Version: v1.0
 */
public class MyException extends RuntimeException {

    /**
     *  错误编码
     */
    public String code;

    /**
     *  错误信息
     */
    public String msg;

    /**
     * @description: 构造方法
     * @params: [code, msg]
     * @return: 
     * @author: zhangyadong
     * @date: 2021/1/14 15:07
     */
    public MyException(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
