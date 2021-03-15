package com.java.springboot.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

import java.io.IOException;

/**
 * @Description: Scp上传文件工具类
 * @Author: zhangyadong
 * @Date: 2021/3/12 15:31
 * @Version: v1.0
 */
public class ScpUtil {

    private  String ip;
    private  String linuxUser = "root";
    private  String linuxPwd = "wandatpd";

    /**
     * @description: 构造方法
     * @params: [ip]
     * @return:
     * @author: zhangyadong
     * @date: 2021/3/12 15:54
     */
    public ScpUtil(String ip) {
        this.ip = ip;
    }

    /**
     * @description: 构造方法
     * @params: [ip, liuxUser, liuxPawd]
     * @return:
     * @author: zhangyadong
     * @date: 2021/3/12 15:54
     */
    public ScpUtil(String ip, String linuxUser, String linuxPwd) {
        this.ip = ip;
        this.linuxUser = linuxUser;
        this.linuxPwd = linuxPwd;
    }

    /**
     * @description: 下载文件
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2021/3/12 15:39
     */
    public void downloadFile(String remoteFile, String localDir) {
        Connection conn = new Connection(ip);
        Session session = null;// 打开一个会话
        try {
            conn.connect();//建立连接
            boolean login = conn.authenticateWithPassword(linuxUser, linuxPwd);//根据用户名密码，进行校验
            if (login) {
                System.out.println("SSH登录成功");
            } else {
                System.out.println("SSH登录失败！");
                return;
            }
            SCPClient scpClient = conn.createSCPClient();
            //从远程机器获取文件 参数对应:["远程文件/root/test.txt", "本地文件夹"]
            scpClient.get(remoteFile,localDir);
            conn.close();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @description: 上传文件
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2021/3/12 15:33
     */
    public void uploadFile(String uploadFile, String remoteDir) {
        Connection conn = new Connection(ip);
        try {
            //建立连接
            conn.connect();
            //根据用户名密码，进行校验
            boolean login = conn.authenticateWithPassword(linuxUser, linuxPwd);
            if (login) {
                System.out.println("SSH登录成功");
            } else {
                System.out.println("SSH登录失败");
                return;
            }
            SCPClient scpClient = conn.createSCPClient();
            //上传文件 参数对应:["本地文件test.txt", "远程机器目录/root/scp"]
            scpClient.put(uploadFile, remoteDir);
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //        downloadFile();
        //uploadFile();
    }
}
