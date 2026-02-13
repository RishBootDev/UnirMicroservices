const axios = require('axios');

const GATEWAY_URL = process.env.GATEWAY_URL || 'http://localhost:9000/api/unir';

exports.getUserProfile = async (userId, authToken) => {
  try {
    const config = authToken ? { headers: { Authorization: authToken } } : {};
    const response = await axios.get(`${GATEWAY_URL}/profile/${userId}`, config);
    return response.data;
  } catch (error) {
    console.error(`Error fetching profile for user ${userId}:`, error.message);
    return null;
  }
};
