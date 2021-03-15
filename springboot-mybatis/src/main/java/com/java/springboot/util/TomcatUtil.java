package com.java.springboot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * <p>
 *     tomcat重启工具类
 * </p>
 */
@Slf4j
public class TomcatUtil {

    //软件安装目录
    private String tomcatDir;

    //重试次数
    private int retryTime;

    //重启关闭线程等待时间(10s)
    private String waitingTime = "10000";

    /**
     * 构造函数
     *
     * @param tomcatDir
     * @param retryTime
     */
    public TomcatUtil(String tomcatDir, int retryTime) {
        this.tomcatDir = tomcatDir;
        this.retryTime = retryTime;
    }

    /**
     * 执行tomcat重启
     * @param ipAddres
     * @param userName
     * @param password
     * @param tomcatDir
     * @return
     */
    public static boolean exec(String ipAddres, String userName, String password, String tomcatDir) {
        boolean isOk = false;
        SSHUtil sshUtil = null;
        TomcatUtil tomcatUtil = null;
        try {
            //建立ssh链接
            sshUtil = new SSHUtil(ipAddres, userName, password);
            //重启tomcat
            tomcatUtil = new TomcatUtil(tomcatDir, 10);
            tomcatUtil.restartTomcat(sshUtil);
            sshUtil.close();
            sshUtil = null;
            isOk = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (sshUtil != null) {
                try {
                    sshUtil.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                sshUtil = null;
            }
        }
        return isOk;
    }

    /**
     * 重启Tomcat
     *
     * @param sshUtil
     * @return
     * @throws IOException
     */
    public boolean restartTomcat(SSHUtil sshUtil) throws Exception {
        //结束tomcat进程
        log.info("开始执行重启Tomcat...");
        for (int i = 0; i < retryTime; i++) {
            log.info("执行关闭Tomcat第" + (i + 1) + "次...");
            if (isExistTomcatProcess(sshUtil)) {
                try {
                    //强制结束进程
                    log.info("kill命令强制结束进程关闭Tomcat...");
                    killTomcatBySoft(sshUtil);
                    log.info("kill命令关闭Tomcat后等待" + waitingTime + "毫秒。。。");
                    Thread.sleep(Integer.valueOf(waitingTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.info("执行关闭Tomcat第" + (i + 1) + "次...异常：", e);
                }
            } else {
                log.info("未发现需要执行关闭Tomcat第" + (i + 1) + "次...");
                break;
            }
        }
        log.info("执行关闭Tomcat完成！");

        //启动tomcat
        for (int i = 0; i < retryTime; i++) {
            log.info("执行启动Tomcat第" + (i + 1) + "次...");
            if (!isExistTomcatProcess(sshUtil)) {
                //调用tomcat自身脚本重启程序
                startupTomcat(sshUtil);
                try {
                    log.info("启动Tomcat后等待" + waitingTime + "毫秒。。。");
                    Thread.sleep(Integer.valueOf(waitingTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.info("执行启动Tomcat第" + (i + 1) + "次...异常：", e);
                }
            } else {
                log.info("执行启动Tomcat第" + (i + 1) + "次...");
                break;
            }
        }

        if (isExistTomcatProcess(sshUtil)) {
            log.info("重启tomcat成功！");
            return true;
        } else {
            log.info("重启tomcat失败！");
            return false;
        }

    }

    /**
     * 判断是否含有tomcat进程
     *
     * @param sshUtil
     * @return
     * @throws IOException
     */
    public boolean isExistTomcatProcess(SSHUtil sshUtil) throws Exception {
        String command = "ps -ef | grep " + this.tomcatDir.split("/")[this.tomcatDir.split("/").length - 1] + "/ | grep -v grep | awk '{print $2}';";
        return isExistProcess(sshUtil, command);
    }

    /**
     * 判断当前进程中是否含有program
     *
     * @param sshUtil
     * @param command
     * @return
     * @throws IOException
     */
    public boolean isExistProcess(SSHUtil sshUtil, String command) throws Exception {
        boolean isExistTomcatProcess = false;
        String res = sshUtil.execCmd(command);
        if (res.length() > 0) {
            isExistTomcatProcess = true;
        }
        log.info("判断是否含有tomcat进程:" + isExistTomcatProcess);
        return isExistTomcatProcess;
    }


    /**
     * 启动Tomcat
     *
     * @param sshUtil
     * @throws IOException
     */
    public void startupTomcat(SSHUtil sshUtil) throws Exception {
        log.info("执行启动Tomcat脚本程序:" + this.tomcatDir + "bin/startup.sh");
        sshUtil.runShell(tomcatDir + "bin/startup.sh\n");
    }


    /**
     * 强制kill结束Tomcat进程
     *
     * @param sshUtil
     * @throws IOException
     */
    public void killTomcatBySoft(SSHUtil sshUtil) throws Exception {
        String command = "ps -ef | grep " + this.tomcatDir.split("/")[this.tomcatDir.split("/").length - 1] + "/ | grep -v grep | awk '{print $2}';";
        String res = sshUtil.execCmd(command);
        if (res.length() > 0) {
            sshUtil.execCmd("kill -9 " + res);
        }
        log.info("执行kill 命令，关闭Tomcat");
    }

    public String getTomcatDir() {
        return tomcatDir;
    }

    public void setTomcatDir(String tomcatDir) {
        this.tomcatDir = tomcatDir;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }
}
