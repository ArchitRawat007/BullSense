import axios from 'axios';

const API_URL = "http://localhost:5000/predict";  // Flask API endpoint for prediction

const getStockPrediction = async (stockValue) => {
    try {
        const response = await axios.post(API_URL, {
            stock_value: stockValue
        });
        return response.data.predicted_stock_value;
    } catch (error) {
        console.error("Error fetching prediction:", error);
        return "Prediction failed";
    }
};

export default { getStockPrediction };
