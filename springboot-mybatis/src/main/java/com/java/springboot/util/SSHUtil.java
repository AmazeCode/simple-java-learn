package com.java.springboot.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 *     SSH连接linux服务器操作命令工具类
 * </p>
 */
public class SSHUtil {

    private Channel channel;
    private Session session = null;

    public SSHUtil(final String ipAddress, final String username, final String password) throws Exception {
        JSch jsch = new JSch();
        this.session = jsch.getSession(username, ipAddress, 22);
        this.session.setPassword(password);
        this.session.setConfig("StrictHostKeyChecking", "no");
        this.session.setTimeout(60000);
        this.session.connect();
        this.channel = this.session.openChannel("shell");
        this.channel.connect(30000);
    }

    /**
     * 执行操作命令
     * @param cmd 命令
     * @return
     * @throws Exception
     */
    public String runShell(String cmd) throws Exception {
        String temp = null;

        InputStream instream = null;
        OutputStream outstream = null;
        try {
            instream = this.channel.getInputStream();
            outstream = this.channel.getOutputStream();
            outstream.write(cmd.getBytes());
            outstream.flush();
            TimeUnit.SECONDS.sleep(2);
            if (instream.available() > 0) {
                byte[] data = new byte[instream.available()];
                int nLen = instream.read(data);
                if (nLen < 0) {
                    throw new Exception("network error.");
                }
                temp = new String(data, 0, nLen, "UTF-8");
            }
        }  finally {
            outstream.close();
            instream.close();
        }
        return temp;
    }

    /**
     * 执行相关的命令可返回执行结果
     * @param command 命令
     * @return
     * @throws Exception
     */
    public String execCmd(String command) throws Exception {

        BufferedReader reader = null;
        Channel channel = null;
        StringBuffer sb=new StringBuffer("");

        channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);
        channel.connect();
        InputStream in = channel.getInputStream();
        reader = new BufferedReader(new InputStreamReader(in,
                Charset.forName("utf-8")));
        String buf = null;
        while ((buf = reader.readLine()) != null) {
            sb.append(buf);
        }
        reader.close();
        return sb.toString();
    }

    /**
     * 关闭SSH连接
     */
    public void close() throws Exception {
        this.channel.disconnect();
        this.session.disconnect();
    }
}
