import torch
import torch.nn as nn
import flask
from flask import request, jsonify

# Dummy Model - Replace with your actual PyTorch model
class SimpleStockModel(nn.Module):
    def __init__(self):
        super(SimpleStockModel, self).__init__()
        self.fc1 = nn.Linear(1, 1)  # Simple linear model for stock prediction

    def forward(self, x):
        return self.fc1(x)

# Instantiate model and load weights (replace with actual model loading)
model = SimpleStockModel()
model.load_state_dict(torch.load("stock_model.pth"))
model.eval()

app = flask.Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Get data from the request
        data = request.get_json()
        stock_value = data.get("stock_value")  # Example: stock_value = [100.5]

        if not stock_value:
            return jsonify({"error": "Stock value is required"}), 400

        # Prepare input for the model (convert stock value to tensor)
        input_tensor = torch.tensor([[stock_value]], dtype=torch.float32)

        # Make the prediction
        with torch.no_grad():
            prediction = model(input_tensor).item()  # Get the predicted value

        # Return prediction as JSON response
        return jsonify({"predicted_stock_value": prediction})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
