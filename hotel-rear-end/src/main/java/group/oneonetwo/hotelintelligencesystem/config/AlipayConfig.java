package group.oneonetwo.hotelintelligencesystem.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 文
 * @description: 支付宝配置类
 * @date 2022/6/22 8:35
 */
@Component
@ConfigurationProperties(prefix = "alipay", ignoreUnknownFields = false)
public class AlipayConfig {

//    @Value("${alipay.gateway}")
    private String gateway;

    private String appId;

    private String privateKey;

    private String format;

    private String charset;

    private String alipayPublicKey;

    private String signType;

    public AlipayConfig() {

    }

    public AlipayConfig(String gateway, String appId, String privateKey, String format, String charset, String alipayPublicKey, String signType) {
        this.gateway = gateway;
        this.appId = appId;
        this.privateKey = privateKey;
        this.format = format;
        this.charset = charset;
        this.alipayPublicKey = alipayPublicKey;
        this.signType = signType;
    }

//    暂时注释支付宝相关代码
    /**
     * 获取alipay客户端
     * @return
     */
    public AlipayClient getAlipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(gateway,appId,privateKey,format,charset,alipayPublicKey,signType);
        return alipayClient;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
