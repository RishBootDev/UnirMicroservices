const express = require('express');
const app = express();
const cors = require('cors');
const messageRoutes = require('./routes/message.routes');

app.use(cors());
app.use(express.json());

app.use('/messages', messageRoutes);
app.use('/health', (req, res) => res.status(200).send('OK'));

module.exports = app;