class WebSocketService {
    constructor() {
        this.socket = null;
        this.callback = null;
    }

    connect(url, callback) {
        this.socket = new WebSocket(url);
        this.socket.onopen = () => {
            console.log("WebSocket connected");
        };

        this.socket.onmessage = (message) => {
            console.log("Received: ", message.data);
            if (this.callback) {
                this.callback(message.data);
            }
        };

        this.socket.onclose = () => {
            console.log("WebSocket closed");
        };

        this.callback = callback;
    }

    sendMessage(message) {
        if (this.socket && this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(message);
        } else {
            console.log("WebSocket not connected");
        }
    }

    disconnect() {
        if (this.socket) {
            this.socket.close();
        }
    }
}

export default new WebSocketService();
