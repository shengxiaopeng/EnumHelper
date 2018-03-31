package Jsch;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2018/1/7.
 */
public class JschTest {

    public static void main(String[] args) throws Exception {
        String user = "shengxiaopeng";
        String passwd = "Sheng_901108Sheng_901108..";
        String host = "192.168.13.213";

        JSchDemo demo = new JSchDemo(user, passwd, host);
        demo.connect();
        demo.execCmd();
    }

    static class JSchDemo {
        private String charset = "UTF-8"; // 设置编码格式
        private String user; // 用户名
        private String passwd; // 登录密码
        private String host; // 主机IP
        private JSch jsch;
        private Session session;

        /**
         * @param user 用户名
         * @param passwd 密码
         * @param host 主机IP

         */
        public JSchDemo(String user, String passwd, String host) {
            this.user = user;
            this.passwd = passwd;
            this.host = host;
        }

        /**
         * 连接到指定的IP
         *
         * @throws JSchException
         */
        public void connect() throws JSchException {
            jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setPassword(passwd);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        }

        /**
         * 执行相关的命令
         */
        public void execCmd() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            //String command = "ls ;rm -f aa.log; ls ";
            //String command = "tar -czf aa.tar.gz aa.log";
            //String command = "tar -zxvf aa.tar.gz";
            //String command = "tar -zxvf aa.tar.gz";
            //String command = "/root/test.sh";
            //String command = "reboot";
            //String command = "echo abc >> xx.txt";
            String command = "ls -al";

            //String command = "echo \"always\" >/sys/kernel/mm/transparent_hugepage/enabled ";
            BufferedReader reader = null;
            Channel channel = null;

            try {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
                String buf = null;
                while ((buf = reader.readLine()) != null) {
                    System.out.println(buf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSchException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                channel.disconnect();
                session.disconnect();
            }
        }

    }




}
