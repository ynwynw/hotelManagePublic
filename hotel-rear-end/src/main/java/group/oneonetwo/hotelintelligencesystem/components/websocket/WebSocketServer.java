package group.oneonetwo.hotelintelligencesystem.components.websocket;

import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 文
 * @description websocket服务类
 */
@ServerEndpoint("/wsServer")
@Component
public class WebSocketServer {

    static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收uid*/
    private String uid = "";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
//        String uid = SecurityContextHolder.getContext().getAuthentication().getName();\
        String uid = session.getUserPrincipal().getName();
        this.uid = uid;
        if(webSocketMap.containsKey(uid)){
            webSocketMap.remove(uid);
            webSocketMap.put(uid,this);
            //加入set中
        }else{
            webSocketMap.put(uid,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("websocket: 用户连接:"+uid+",当前在线人数为:" + getOnlineCount());

        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket: 用户:"+uid+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(uid)){
            webSocketMap.remove(uid);
            //从set中删除
            subOnlineCount();
        }
        log.info("websocket: 用户退出:" + uid + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("websocket: 用户消息:"+uid+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(!WStringUtils.isBlank(message)){
            try {
                //解析发送的报文
                Gson gson = new Gson();
                Map<String,String> map = gson.fromJson(message, HashMap.class);
                //追加发送人(防止串改)
                map.put("fromUserId",this.uid);
                String toUserId = map.get("toUserId");
                //传送给对应toUserId用户的websocket
                if(!WStringUtils.isBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
                    webSocketMap.get(toUserId).sendMessage(gson.toJson(map));
                }else{
                    log.error("websocket: 请求的uid:"+toUserId+"不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket: 用户错误:"+this.uid+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,String uid) throws IOException {
        log.info("websocket: 发送消息到:"+ uid +"，报文:"+message);
        if(!WStringUtils.isBlank(uid) && webSocketMap.containsKey(uid)){
            webSocketMap.get(uid).sendMessage(message);
        }else{
            log.error("websocket: 用户"+ uid +",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
