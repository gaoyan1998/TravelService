package Control;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by ikiler on 2019/2/23.
 * Email : ikiler@126.com
 */
public class SendEmailCode extends Thread{
    // 收件人电子邮箱
    String to = "";

    // 发件人电子邮箱
    String from = "";

    // 指定发送邮件的主机
    String host = "";

    // 获取系统属性
    Properties properties = System.getProperties();
    //获取配置文件
    Properties config = new Properties();

    String msg;


    public SendEmailCode(String to, String msg) {
        this.to = to;
        this.msg = msg;
    }

    @Override
    public void run() {
        super.run();
        send(to,msg);
    }

    public void send(String email, String code) {
        //加载配置
        try {
            config.load(SendEmailCode.class.getClassLoader().getResourceAsStream("config.properties"));
            from = config.getProperty("email");
            to = email;
            host = config.getProperty("host");
            // 设置邮件服务器
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");
            // 获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, config.getProperty("passcode")); //发件人邮件用户名、授权码
                }
            });
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("旅游助手验证码");

            // 设置消息体
            message.setText("您的验证码为："+msg);

            // 发送消息
            Transport.send(message);
            System.out.println("SendEmailCode：：Sent message successfully....");
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
}
