package com.bullsenseai.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.stereotype.Component;
import com.bullsenseai.ai.StockPredictionService;

@Component
public class StockChatWebSocketHandler implements WebSocketHandler {

    private final StockPredictionService stockPredictionService;

    // Constructor injection of StockPredictionService
    public StockChatWebSocketHandler(StockPredictionService stockPredictionService) {
        this.stockPredictionService = stockPredictionService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // This method is called when a new WebSocket connection is established
        System.out.println("New WebSocket connection established.");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Ensure the incoming message is a TextMessage
        if (message instanceof TextMessage) {
            String stockData = ((TextMessage) message).getPayload();  // Incoming message (e.g., stock symbol or data)

            // Call the StockPredictionService to get the stock prediction
            String prediction = stockPredictionService.predict(stockData);

            // Send the prediction back to the client
            session.sendMessage(new TextMessage("Stock prediction: " + prediction));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Handle any transport errors
        System.out.println("Error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus closeStatus) throws Exception {
        // This method is called when the WebSocket connection is closed
        System.out.println("WebSocket connection closed.");
    }
    // Correct implementation of supportsPartialMessages()
    @Override
    public boolean supportsPartialMessages() {
        // If you want to support partial messages (messages split into parts), return true.
        // Most applications don't require this, so we can return false here.
        return false;
    }
}

