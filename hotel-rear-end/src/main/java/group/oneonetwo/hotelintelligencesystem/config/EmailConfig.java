package group.oneonetwo.hotelintelligencesystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 文
 * @description: 邮件发送配置类
 * @date 2022/4/19 20:59
 */
@Component
@ConfigurationProperties(prefix = "email-group", ignoreUnknownFields = false)
public class EmailConfig {

    private Map<String,MailProperties> configs;

    public Map<String, MailProperties> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, MailProperties> configs) {
        this.configs = configs;
    }


    public static class MailProperties {
        private String host;
        private String username;
        private String password;
        private String senderName;
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        @Override
        public String toString() {
            return "MailProperties{" +
                    "host='" + host + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", senderName='" + senderName + '\'' +
                    '}';
        }
    }


}

