const express = require('express');
const router = express.Router();
const messageController = require('../controllers/message.controller');
const mongoose = require('mongoose'); // Needed for the aggregation in controller if we moved it there, but here just robust


router.get('/', messageController.getConversations);
router.get('/:conversationId', messageController.getMessages);
router.post('/:conversationId', messageController.sendMessage);

module.exports = router;
