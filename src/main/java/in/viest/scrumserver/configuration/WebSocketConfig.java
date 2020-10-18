package in.viest.scrumserver.configuration      ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info("WebSocket configuration: register stomp endpoint /stomp");
        registry.addEndpoint("/scrumpoker").setAllowedOrigins("*");
        //.withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        log.info("WebSocket configuration: configure message broker");
        config.setApplicationDestinationPrefixes("/poker");
        // config.enableSimpleBroker("/room", "/queue");
        config.enableSimpleBroker("/room");
    }
}
