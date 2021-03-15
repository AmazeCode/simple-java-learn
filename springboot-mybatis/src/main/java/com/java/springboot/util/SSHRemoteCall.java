package com.java.springboot.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * <p>
 *     exec用于Linux执行命令;
 *     sftp用于文件处理(包括查看/搜索目录，创建文件夹和组织文件，删除或重命名文件等),sftp是优秀的协议.此外,sftp还支持断点续传,这在网络连接不佳的环境中将大有帮助
 * </p>
 * @Author: zhangyadong
 * @Date: 2021/3/3 9:18
 * @Version: v1.0
 */
@Slf4j
public class SSHRemoteCall {

    private static final int DEFAULT_PORT = 22;// 默认端口号
    private int port;// 端口号

    private static String ipAddress = "10.199.213.93";// ip地址
    private static String userName = "root";// 账号
    private static String password = "wandatpd";// 密码

    private Session session;// JSCH session
    private boolean logined = false;// 是否登陆

    // 私有的对象
    private static SSHRemoteCall sshRemoteCall;

    /**
     * 私有的构造方法
     */
    private SSHRemoteCall() {
    }

    // 懒汉式,线程不安全,适合单线程
    public static SSHRemoteCall getInstance() {
        if (sshRemoteCall == null) {
            sshRemoteCall = new SSHRemoteCall();
        }
        return sshRemoteCall;
    }

    // 懒汉式,线程安全,适合多线程
    public static synchronized SSHRemoteCall getInstance2() {
        if (sshRemoteCall == null) {
            sshRemoteCall = new SSHRemoteCall();
        }
        return sshRemoteCall;
    }

    /**
     * @description: 构造方法,可以直接使用DEFAULT_PORT
     * @params: [ipAddress, userName, password]
     * @author: zhangyadong
     * @date: 2021/3/11 16:11
     */
    public SSHRemoteCall(String ipAddress, String userName, String password) {
        this(ipAddress, DEFAULT_PORT, userName, password);
    }

    /**
     * @description: 构造方法,方便直接传入ipAddress,userName,password进行调用
     * @params: [ipAddress, port, userName, password]
     * @author: zhangyadong
     * @date: 2021/3/11 16:10
     */
    public SSHRemoteCall(String ipAddress, int port, String userName, String password) {
        super();
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }

    /**
     * @description: 远程登陆
     * @params: [ipAddress, userName, password]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/3/11 16:10
     */
    public void sshRemoteCallLogin(String ipAddress, String userName, String password) throws Exception {
        // 如果登陆就直接返回
        if (logined) {
            return;
        }
        // 创建jSch对象
        JSch jSch = new JSch();
        try {
            // 获取到jSch的session, 根据用户名、主机ip、端口号获取一个Session对象
            session = jSch.getSession(userName, ipAddress, DEFAULT_PORT);
            // 设置密码
            session.setPassword(password);

            // 方式一,通过Session建立连接
            // session.setConfig("StrictHostKeyChecking", "no");
            // session.connect();

            // 方式二,通过Session建立连接;
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);// 为Session对象设置properties
            //session.setTimeout(3000);// 设置超时
            session.connect();//// 通过Session建立连接
            // 设置登陆状态
            logined = true;
            log.info("主机登录成功！");
        } catch (JSchException e) {
            // 设置登陆状态为false
            logined = false;
            throw new Exception(
                    "主机登录失败, IP = " + ipAddress + ", USERNAME = " + userName + ", Exception:" + e.getMessage());

        }
    }

    /**
     * @description: 关闭连接
     * @params: []
     * @return: void
     * @author: zhangyadong
     * @date: 2021/3/11 16:17
     */
    public void closeSession() {
        // 调用session的关闭连接的方法
        if (session != null) {
            // 如果session不为空,调用session的关闭连接的方法
            session.disconnect();
        }
    }

    /**
     * @description: 执行相关的命令
     * @params: [command]
     * @return: void
     * @author: zhangyadong
     * @date: 2021/3/11 16:17
     */
    public void execCommand(String command) throws IOException {
        InputStream in = null;// 输入流(读)
        Channel channel = null;// 定义channel变量
        try {
            // 如果命令command不等于null
            if (command != null) {
                // 打开channel
                //说明：exec用于执行命令;sftp用于文件处理
                channel = session.openChannel("exec");
                // 设置command
                ((ChannelExec) channel).setCommand(command);
                // channel进行连接
                channel.connect();
                // 获取到输入流
                in = channel.getInputStream();
                // 执行相关的命令
                String processDataStream = processDataStream(in);
                // 打印相关的命令
                log.info("打印相关返回命令:"+processDataStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("命令执行异常:{}",e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    /**
     * @description: 对将要执行的linux的命令进行遍历
     * @params: [in]
     * @return: java.lang.String
     * @author: zhangyadong
     * @date: 2021/3/11 16:24
     */
    public String processDataStream(InputStream in) throws Exception {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = "";
        try {
            while ((result = br.readLine()) != null) {
                sb.append(result);
                // System.out.println(sb.toString());
            }
        } catch (Exception e) {
            throw new Exception("获取数据流失败: " + e);
        } finally {
            br.close();
        }
        return sb.toString();
    }

    /**
     * <p>
     *     targetFile: 上传后显示在远程服务的文件,不存在会新建存在会覆盖
     *     上传文件 可参考:https://www.cnblogs.com/longyg/archive/2012/06/25/2556576.html
     * </p>
     * @param sourceFile 需要上传的文件路径
     * @param targetFile 上传后远程服务器上的文件路径
     */
    public void uploadFile(String sourceFile, String targetFile) throws Exception {
        try {
            // 打开channelSftp
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            // 远程连接
            channelSftp.connect();
            // 创建一个文件名称uploadFile的文件
            File file = new File(sourceFile);
            // 将文件进行上传(sftp协议)
            // 采用默认的传输模式:OVERWRITE
            channelSftp.put(new FileInputStream(file), targetFile, ChannelSftp.OVERWRITE);
            // 切断远程连接
            channelSftp.exit();
            log.info("[{}]文件上传成功",file.getName());
        } catch (Exception e) {
            log.info("文件上传失败:{}",e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 下载文件 采用默认的传输模式：OVERWRITE
     * @param src linux服务器文件地址
     * @param dst 本地存放地址
     * @throws JSchException
     * @throws SftpException
     */
    public void fileDownload(String src, String dst) throws JSchException, SftpException {
        // src 是linux服务器文件地址,dst 本地存放地址
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 下载文件,多个重载方法
        channelSftp.get(src, dst);
        // 切断远程连接,quit()等同于exit(),都是调用disconnect()
        channelSftp.quit();
        // channelSftp.disconnect();
        log.info("[{}]下载文件成功",src);
    }

    /**
     * 删除文件
     * @param directoryFile 要删除的文件
     * @throws SftpException
     * @throws JSchException
     */
    public void deleteFile(String directoryFile) throws SftpException, JSchException {
        // 打开openChannel的sftp
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 删除文件
        channelSftp.rm(directoryFile);
        // 切断远程连接
        channelSftp.exit();
        log.info("[{}]删除文件成功",directoryFile);
    }

    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     * @throws JSchException
     */
    public Vector listFiles(String directory) throws JSchException, SftpException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 显示目录信息
        Vector ls = channelSftp.ls(directory);
        log.info("文件："+ls);
        // 切断连接
        channelSftp.exit();
        return ls;
    }


    public static void main(String[] args) {
        // 连接到指定的服务器
        try {
            // 1、首先远程连接ssh
            SSHRemoteCall.getInstance().sshRemoteCallLogin(ipAddress, userName, password);
            // 打印信息
            System.out.println("0、连接10.199.213.93,ip地址: " + ipAddress + ",账号: " + userName + ",连接成功.....");

            // 2、执行相关的命令
            // 查看目录信息
            // String command = "ls /home/hadoop/package ";
            // 查看文件信息
            // String command = "cat /home/hadoop/package/test ";
            // 查看磁盘空间大小
            // String command = "df -lh ";
            // 查看cpu的使用情况
            // String command = "top -bn 1 -i -c ";
            // 查看内存的使用情况
            /*String command = "free ";
            SSHRemoteCall.getInstance().execCommand(command);*/

            // 3、上传文件
            String directory = "/data/jmsQueue.properties";// 目标文件名
            String uploadFile = "D:\\jmsQueue.properties";// 本地文件名
            SSHRemoteCall.getInstance().uploadFile(directory, uploadFile);

            // 4、下载文件
            // src 是linux服务器文件地址,dst 本地存放地址,采用默认的传输模式：OVERWRITE
            //test为文件名称哈
            /*String src = "/home/hadoop/package/test";
            String dst = "E:\\";
            SSHRemoteCall.getInstance().fileDownload(src, dst);*/

            // 5、刪除文件
            /*String deleteDirectoryFile = "/home/hadoop/package/test";
            SSHRemoteCall.getInstance().deleteFile(deleteDirectoryFile);*/

            // 6、展示目录下的文件信息
            /*String lsDirectory = "/home/hadoop/package";
            SSHRemoteCall.getInstance().listFiles(lsDirectory);*/

            // 7、关闭连接
            SSHRemoteCall.getInstance().closeSession();
        } catch (Exception e) {
            // 打印错误信息
            System.err.println("远程连接失败......");
            e.printStackTrace();
        }
    }

}
