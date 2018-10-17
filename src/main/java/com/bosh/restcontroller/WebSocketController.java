/**
 * 
 */
package com.bosh.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.dto.SocketMessage;

/**
 * WebSocket接口  用于服务器端对客户都进行消息推送
 * 
 * @author wangmt
 * @date 2018年9月21日
 */
@RestController
public class WebSocketController {
	
	public SimpMessagingTemplate template;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * WebSocket广播接口：客户端可以在/topic/all监听并接受服务端发回的消息
     * @param message
     * @return
     */
    @SendTo("/topic/all")
    public SocketMessage sendToAll(SocketMessage message){
        return message;
    }

    /**
     * WebSocket单播：客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message”
     * @param message
     * @return
     */
    @SendToUser("/message")
    public SocketMessage sendToUser(SocketMessage message){
        return message;
    }


}
