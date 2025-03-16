package com.bullsenseai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import com.bullsenseai.websocket.StockChatWebSocketHandler;
import com.bullsenseai.ai.StockPredictionService;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final StockPredictionService stockPredictionService;

    @Autowired
    public WebSocketConfig(StockPredictionService stockPredictionService) {
        this.stockPredictionService = stockPredictionService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Pass the StockPredictionService to the WebSocket handler
        registry.addHandler(new StockChatWebSocketHandler(stockPredictionService), "/chat").setAllowedOrigins("*");
    }
}



