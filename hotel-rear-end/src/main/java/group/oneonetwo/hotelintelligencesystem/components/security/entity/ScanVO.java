package group.oneonetwo.hotelintelligencesystem.components.security.entity;

/**
 * @author 文
 */
public class ScanVO {
    public String type;

    public String qrCode;

    public String token;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
