package com.java.mybatis.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Sql工具类
 * @Author: zhangyadong
 * @Date: 2021/1/2 17:58
 * @Version: v1.0
 */
public class SqlUtils {

    /**
     * @description: 获取Insert语句后面values 参数信息
     * @params: [sql]
     * @return: java.lang.String[]
     * @author: zhangyadong
     * @date: 2021/1/2 18:03
     */
    public static String[] sqlInsertParameter(String sql) {
        int startIndex = sql.indexOf("values");
        int endIndex = sql.length();
        String subString  = sql.substring(startIndex + 6, endIndex).replace("(","").replace(")","").replace("#{","").replace("}","");
        String[] split = subString.split(",");
        return split;
    }

    /**
     * @description: 获取Select后面Where条件参数
     * @params: [sql]
     * @return: java.util.List<java.lang.String>
     * @author: zhangyadong
     * @date: 2021/1/2 22:04
     */
    public static List<String> sqlSelectParameter(String sql) {
        int startIndex = sql.indexOf("where");
        int endIndex = sql.length();
        String substring = sql.substring(startIndex + 5, endIndex);
        String[] split = substring.split("and");
        List<String> listArr = new ArrayList<>();
        for (String string : split) {
            String[] sp2 = string.split("=");
            listArr.add(sp2[0].trim());
        }
        return listArr;
    }

    /**
     * @description: 将SQL语句替换参数为?
     * @params: [sql, parameterName]
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/2 20:14
     */
    public static String paramQuestion (String sql, String[] parameterName) {
        for (int i = 0; i < parameterName.length; i++) {
            String string = parameterName[i];
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }

    /**
     * @description: 将SQL语句替换参数为?
     * @params: [sql, parameterName]
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/1/2 20:14
     */
    public static String paramQuestion(String sql, List<String> parameterName) {
        for (int i = 0; i < parameterName.size(); i++) {
            String string = parameterName.get(i);
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }
}
