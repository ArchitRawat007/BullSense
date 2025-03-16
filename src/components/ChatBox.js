import React, { useState, useEffect } from 'react';
import WebSocketService from '../services/WebSocketService';
import axios from 'axios';

const ChatBox = () => {
    const [message, setMessage] = useState('');
    const [response, setResponse] = useState('');
    const [socketConnected, setSocketConnected] = useState(false);

    useEffect(() => {
        // Connect to the WebSocket server
        WebSocketService.connect("ws://localhost:8080/chat", (msg) => {
            setResponse(msg);  // Set the message received from WebSocket
        });
        setSocketConnected(true);

        // Cleanup on component unmount
        return () => {
            WebSocketService.disconnect();
        };
    }, []);

    const handleMessageSubmit = (e) => {
        const handleMessageSubmit = async (e) => {
            e.preventDefault();
            if (message.trim()) {
                // Call Flask API for prediction (optional)
                const prediction = await StockPredictionService.getStockPrediction(message);
                // Send message to WebSocket server with prediction result
                const chatMessage = `${message}: Predicted value is ${prediction}`;
                WebSocketService.sendMessage(chatMessage);
                setMessage('');
            }
        };

    };

    return (
        <div>
            <h1>Stock Trade Chatbot</h1>
            <div>
                <input
                    type="text"
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Enter Stock Symbol (e.g., AAPL)"
                />
                <button onClick={handleMessageSubmit}>Send</button>
            </div>
            {response && (
                <div>
                    <h3>Prediction Response:</h3>
                    <p>{response}</p>
                </div>
            )}
        </div>
    );
};

export default ChatBox;
