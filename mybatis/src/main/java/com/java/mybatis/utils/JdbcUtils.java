package com.java.mybatis.utils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

/**
 * @Description: Jdbc工具类
 * @Author: zhangyadong
 * @Date: 2021/1/2 16:41
 * @Version: v1.0
 */
public class JdbcUtils {

    public static String DRIVER = "com.mysql.jdbc.Driver";
    public static String URL = "jdbc:mysql://localhost:3306/aop?useSSL=false";
    public static String USER_NAME = "root";
    public static String PASSWORD = "root";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private JdbcUtils(){

    }
    /**
     * Get connection
     * @return
     */
    public static Connection getconnnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Close connection
     * @param rs
     * @param st
     * @param con
     */
    public static void close(ResultSet rs, Statement st, Connection con){
        try {
            try{
                if(rs!=null){
                    rs.close();
                }
            }finally{
                try{
                    if(st!=null){
                        st.close();
                    }
                }finally{
                    if(con!=null)
                        con.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection
     * @param rs
     */
    public static void close(ResultSet rs){
        Statement st = null;
        Connection con = null;
        try {
            try {
                if(rs != null){
                    st = rs.getStatement();
                    rs.close();
                }
            } finally{
                try{
                    if(st != null){
                        con = st.getConnection();
                        st.close();
                    }
                }finally{
                    if(con != null){
                        con.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection
     * @param st
     * @param con
     */
    public static void close(Statement st,Connection con){
        try {
            try{
                if(st!=null){
                    st.close();
                }
            }finally{
                if(con!=null)
                    con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @description: 新增
     * @params: [sql, args]
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/2 20:30
     */
    public static int insert(String sql,List<Object> args){
        int result = 0;
        Connection con = getconnnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            if(args != null){
                for (int i = 0; i < args.size(); i++) {
                    ps.setObject((i+1), args.get(i));
                }
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(ps,con);
        }

        return result;
    }

    /**
     * @description: 删除方法
     * @params: [sql, args]
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/2 20:30
     */
    public static int delete(String sql,Object... args){
        int result = 0;
        Connection con = getconnnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            if(args != null){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject((i+1), args[i]);
                }
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(ps,con);
        }
        return result;
    }

    /**
     * @description: 修改（insert/delete/update都可用这个方法）
     * @params: [sql, args]
     * @return: int
     * @author: zhangyadong
     * @date: 2021/1/2 20:30
     */
    public static int update(String sql,Object... args){
        int result = 0;
        Connection con = getconnnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            if(args != null){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject((i+1), args[i]);
                }
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(ps,con);
        }

        return result;
    }

    /**
     * query, because need to manually close the resource, so not recommended for use it
     * @param sql
     * @param args
     * @return ResultSet
     */
    @Deprecated
    public static ResultSet query(String sql,Object... args){
        ResultSet result = null;
        Connection con = getconnnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            if(args != null){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject((i+1), args[i]);
                }
            }
            result = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *Query a single record
     * @param sql
     * @param args
     * @return Map<String,Object>
     */
    public static Map<String,Object> queryForMap(String sql, Object... args){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = queryForList(sql, args);
        if(list.size() > 0){
            result = list.get(0);
        }
        return result;
    }

    /**
     * Query a single record
     * @param sql
     * @param args
     * @return <T>
     */
    public static <T> T queryForObject(String sql,Class<T> clz,Object... args){
        T result = null;
        List<T> list =queryForList(sql, clz, args);
        if(list.size()>0){
            result = list.get(0);
        }
        return result;
    }

    /**
     * Query a single record
     * @param sql
     * @param args
     * @return List<Map<String,Object>>
     */
    public static List<Map<String,Object>> queryForList(String sql,Object... args){
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            con = getconnnection();
            ps = con.prepareStatement(sql);
            if(args != null){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject((i+1), args[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(rs.next()){
                Map<String,Object> map = new HashMap<String,Object>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close(rs, ps, con);
        }
        return result;
    }

    /**
     * Query a single record
     * @param sql
     * @param args
     * @return List<T>
     */
    public static <T> List<T> queryForList(String sql,Class<T> clz,Object... args){
        List<T> result = new ArrayList<T>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getconnnection();
            ps = con.prepareStatement(sql);
            if(args != null){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject((i+1), args[i]);
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(rs.next()){
                T obj = clz.newInstance();
                for (int i = 1; i <= columnCount ; i++) {
                    String columnName = rsmd.getColumnName(i);
                    String methodName = "set"+columnName.substring(0,1).toUpperCase()+
                            columnName.substring(1, columnName.length());
                    Method method [] = clz.getMethods();
                    for (Method meth : method) {
                        if(methodName.equals(meth.getName())){
                            meth.invoke(obj, rs.getObject(i));
                        }
                    }
                }
                result.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            close(rs,ps,con);
        }
        return result;
    }
}
