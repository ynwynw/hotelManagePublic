package group.oneonetwo.hotelintelligencesystem.components.security.dto;

/**
 * @author 文
 * @description 登录DTO
 */
public class LoginUser {

    private String username;
    private String password;
    private Integer rememberMe;

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

    public Integer getRememberMe() {
        if (rememberMe == null) {
            this.rememberMe = 0;
        }
        return rememberMe;
    }

    public void setRememberMe(Integer rememberMe) {
        this.rememberMe = rememberMe;
    }
}
