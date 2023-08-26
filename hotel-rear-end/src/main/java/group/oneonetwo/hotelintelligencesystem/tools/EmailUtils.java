package group.oneonetwo.hotelintelligencesystem.tools;

import group.oneonetwo.hotelintelligencesystem.config.EmailConfig;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author 文
 * @description: 发送邮件工具类
 * @date 2022/4/19 21:05
 */
@Component
public class EmailUtils {
    @Autowired
    private EmailConfig emailConfig;

    private List<JavaMailSenderImpl> senderList;

    Logger logger = LoggerFactory.getLogger(Object.class);

    /**
     * 初始化发送者列表,系统启动仅初始化一次
     */
    private void initSenderList() {
        Map<String, EmailConfig.MailProperties> mailConfigs=emailConfig.getConfigs();
        senderList = new ArrayList<>();
        logger.info("初始化mailSender,mailConfigs={}",mailConfigs);
        for (EmailConfig.MailProperties mailProperties : mailConfigs.values()) {
            logger.info("mailConfigs.values(),mailProperties={}",mailProperties);
            // 邮件发送者
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost(mailProperties.getHost());
            //5、SMTP服务器: 默认端口 换成腾讯云服务器后,需要将端口换成465
//            javaMailSender.setPort(25);
            //6、//发送邮件协议名称
            javaMailSender.setProtocol("smtp");
            //7、编码格式
            javaMailSender.setDefaultEncoding("UTF-8");
            javaMailSender.setUsername(mailProperties.getUsername());
            javaMailSender.setPassword(mailProperties.getPassword());
            //8、创建连接对象，连接到邮箱服务器
            Properties properties = new Properties();
            //发送服务器需要身份验证,要采用指定用户名密码的方式去认证
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", true);
            //9、添加连接对象到邮件对象中
            javaMailSender.setJavaMailProperties(properties);
            // 添加数据
            senderList.add(javaMailSender);
        }
    }

    /**
     * 获取MailSender
     *
     * @return CustomMailSender
     */
    public JavaMailSenderImpl getJavaMailSender(String username) {
        if (senderList == null || senderList.size() == 0) {
            synchronized (EmailUtils.class) {
                if (senderList == null || senderList.size() == 0) {
                    initSenderList();
                }
            }
        }
        if (!WStringUtils.isBlank(username)) {
            for (JavaMailSenderImpl javaMailSender : senderList) {
                if (username.equals(javaMailSender.getUsername())) {
                    return javaMailSender;
                }
            }
            throw new CommonException("找不到username为" + username + "的邮箱配置");
        }
        if (senderList.isEmpty()) {
            throw new CommonException("获取发件配置失败");
        }

        // 随机返回一个JavaMailSender
        return senderList.get(new Random().nextInt(senderList.size()));
    }
    /**
     * 发送邮件的方法
     *
     * @Param username :指定发件人
     * @Param toEmail :给谁发邮件（收件人）
     * @Param code : 邮件的激活码
     * @Param subject ： 主题（标题）
     * @Param text  : 内容
     */
    public void sendMail(String username, String toEmail, String code, final String subject, final String text) {
        try {
            JavaMailSenderImpl javaMailSender = getJavaMailSender(username);
            //10、创建
            MimeMessage mimeMessage = getMimeMessage(toEmail, subject, text, javaMailSender);
            //11、发送邮件
            javaMailSender.send(mimeMessage);
            logger.info(javaMailSender.getUsername() + " 发往 " + toEmail + " 邮件发送成功");
        } catch (javax.mail.MessagingException e) {
            logger.error("发往 " + toEmail + " 邮件发送异常", e);
        }
    }

    /**
     * 声明一个Message对象(代表一封邮件),从session中创建
     * @return
     * @throws javax.mail.MessagingException
     */
    private MimeMessage getMimeMessage(String toEmail, String subject, String text, JavaMailSenderImpl javaMailSender) throws javax.mail.MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        //发件人
        mimeMessageHelper.setFrom(javaMailSender.getUsername());
        //收件人  : 可以发送给多个收件人，该方法有一个重载的 数组形参
        mimeMessageHelper.setTo(toEmail);
        //邮件主题
        mimeMessageHelper.setSubject(subject);
        //邮件内容
        mimeMessageHelper.setText(text, true);

        return mimeMessage;
    }

}
