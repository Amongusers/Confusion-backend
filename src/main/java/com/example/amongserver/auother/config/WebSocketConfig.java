package com.example.amongserver.auother.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.example.amongserver.constant.Const.LINK_CHAT;
import static com.example.amongserver.constant.Const.TOPIC;
/*
Класс конфигурации, нужен для возможности работать с WebSockets
*/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*
        запускаем simple broker с перечислением топиков, на которые будем подписываться
        При необходимости можно настраивать сторонние брокеры,
        такие как RabbitMQ
        */
        config.enableSimpleBroker(TOPIC);
        /*
        задаем префиксы для приложения и пользователя
        */
        config.setApplicationDestinationPrefixes(LINK_CHAT);
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
        указываем endpoint, который будет использоваться для подключения сокетов.
        Не забываем включить SockJS.
        При этом надо будет при подключении добавить к адресу /websocket
        */
        registry.addEndpoint(LINK_CHAT)
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
