package group.oneonetwo.hotelintelligencesystem.tools;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class QiNiuUtils {

    private static final Logger logger = LoggerFactory.getLogger(Object.class);

    // 设置需要操作的账号的AK和SK
    @Value("${qiniuyun.ak}")
    private String ACCESS_KEY;
    @Value("${qiniuyun.sk}")
    private String SECRET_KEY;

    // 要上传的空间名称
    @Value("${qiniuyun.bucket}")
    private String BUCKETNAME;

    // 外链默认域名
    @Value("${qiniuyun.domain}")
    private String DOMAIN;

    /**
     * 将图片上传到七牛云
     */
    public String uploadQNImg(InputStream file, String key) {
//        ACCESS_KEY = qiniuyunConfig.getAk();
//        SECRET_KEY = qiniuyunConfig.getSk();
//        BUCKETNAME = qiniuyunConfig.getBucket();
//        DOMAIN = qiniuyunConfig.getDomain();
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Zone.zone2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            //    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(BUCKETNAME);
            try {
                Response response = uploadManager.put(file, createKey(key), upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = DOMAIN + "/" + putRet.key;
                // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                logger.error(r.toString());
//                System.err.println(r.toString());
                try {
                    logger.error(r.bodyString());
//                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    private String createKey(String key) {
        String time = String.valueOf(System.currentTimeMillis());
        key += "_";
        key += time;
        return key;
    }
}

