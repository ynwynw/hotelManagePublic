package group.oneonetwo.hotelintelligencesystem.components.websocket.controller;


import group.oneonetwo.hotelintelligencesystem.components.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("open/test")
public class TestController {

    @GetMapping
    public void testWS(String s,String uid) {
        try {
            WebSocketServer.sendInfo(s,uid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
