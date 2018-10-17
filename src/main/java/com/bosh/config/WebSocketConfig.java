/**
 * 
 */
package com.bosh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 *  配置webSocket服务，使用stomp协议
 * @author wangmt
 * @date 2018年9月25日
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/user");//可以在topic，user域向客户端发送消息
        registry.setUserDestinationPrefix("/user/");//指定用户发送（一对一）的主题前缀是“/user/”
        registry.setApplicationDestinationPrefixes("/app");//客户端向服务端发送时的主题上面需要加"/app"作为前缀；
    }


    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/quarkServer").setAllowedOrigins("*").withSockJS();//stomp节点
    }

}
