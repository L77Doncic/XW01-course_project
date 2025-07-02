package com.xm01.common.utils.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class EmailServer {

    public void send(String[] emailbox, String smtp, String from, String passwd, String subject, String content) {

        try {
            Properties props = new Properties();// 也可用Properties props =
            // System.getProperties();
            props.put("mail.smtp.host", "smtp.sina.cn");// 存储发送邮件服务器的信息
            props.put("mail.smtp.auth", "true");// 同时通过验证
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable","true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session s = Session.getDefaultInstance(props, null);// 根据属性新建一个邮件会话
            s.setDebug(true);

            MimeMessage msg = new MimeMessage(s);// 由邮件会话新建一个消息对象

            InternetAddress fromAddress = new InternetAddress(from);
            msg.setFrom(fromAddress);// 设置发件人

            for (int i = 0; i < emailbox.length; i++) {
                InternetAddress toAddress = new InternetAddress(emailbox[i]);
                msg.addRecipient(Message.RecipientType.BCC, toAddress);
            }// //*****//////////

            msg.setSubject(subject);// 设置主题
            BodyPart bp = new MimeBodyPart();
            bp.setContent(content, "text/html;charset=UTF-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(bp);
            msg.setContent(mp);// 设置信件内容
            msg.setSentDate(new Date());// 设置发信时间

            msg.saveChanges();// 存储邮件信息
            Transport transport = s.getTransport("smtp");
            transport.connect(smtp, from, passwd);// 以smtp方式登录邮箱
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ;
    }

}
