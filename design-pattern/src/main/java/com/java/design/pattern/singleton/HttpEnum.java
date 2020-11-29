package com.java.design.pattern.singleton;

// rpc 返回code msg
public enum HttpEnum {

    HTTP_200(200,"请求成功"),

    HTTP_500(500,"请求失败");
    /*
        构造方法：执行几次?
        多少个枚举属性就执行几次，例如该类种包含HTTP_200,HTTP_500两个属性，那么构造方法就执行两次。
     */
    HttpEnum(Integer httpCode, String httpMsg) {
        System.out.println("HttpEnum 初始化");
        this.httpCode = httpCode;
        this.httpMsg = httpMsg;
    }

    private Integer httpCode;

    private String httpMsg;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getHttpMsg() {
        return httpMsg;
    }

    public void setHttpMsg(String httpMsg) {
        this.httpMsg = httpMsg;
    }

   /*
        使用演示
    */
    public static void main(String[] args) {
        System.out.println(HttpEnum.HTTP_200.httpCode);
        System.out.println(HttpEnum.HTTP_200.httpMsg);
        System.out.println(HttpEnum.HTTP_500.httpCode);
    }
}
