const Message = require('../models/msg.model');
const externalService = require('../services/external.service');

exports.getConversations = async (req, res) => {
  try {
    const userId = req.header('X-User-Id');
    console.log("Fetching conversations for user:", userId);

    if (!userId) {
      return res.status(400).json({ message: "User ID missing" });
    }


    const conversations = await Message.aggregate([
      {
        $match: {
          $or: [
            { sender: userId },
            { receiver: userId }
          ]
        }
      },
      {
        $sort: { createdAt: -1 }
      },
      {
        $group: {
          _id: {
            $cond: [
              { $eq: ["$sender", userId] },
              "$receiver",
              "$sender"
            ]
          },
          lastMessage: { $first: "$$ROOT" }
        }
      },
      {
        $sort: { "lastMessage.createdAt": -1 }
      }
    ]);


    const authToken = req.header('Authorization');
    const populatedConversations = await Promise.all(conversations.map(async (conv) => {
      const partnerId = conv._id;
      const profile = await externalService.getUserProfile(partnerId, authToken);
      
      const name = profile ? (
          (profile.firstName && profile.lastName) ? `${profile.firstName} ${profile.lastName}` : 
          (profile.firstName || profile.lastName || profile.name || "Unknown User")
      ) : "Unknown User";

      return {
        id: partnerId,
        name: name,
        avatar: profile ? (profile.profilePictureUrl || profile.profilePicture) : null,
        lastMessage: conv.lastMessage.text,
        time: conv.lastMessage.createdAt,
        unread: false
      };
    }));

    res.json(populatedConversations);
  } catch (error) {
    console.error("Error fetching conversations:", error);
    res.status(500).json({ message: "Internal server error" });
  }
};


exports.getMessages = async (req, res) => {
  try {
    const userId = req.header('X-User-Id');
    const { conversationId } = req.params;

    if (!userId) {
      return res.status(400).json({ message: "User ID missing" });
    }

    const messages = await Message.find({
      $or: [
        { sender: userId, receiver: conversationId },
        { sender: conversationId, receiver: userId }
      ]
    }).sort({ createdAt: 1 });

    const formattedMessages = messages.map(m => ({
        id: m._id,
        sender: m.sender === userId ? "me" : m.sender,
        content: m.text,
        time: m.createdAt,

    }));

    res.json(formattedMessages);
  } catch (error) {
    console.error("Error fetching messages:", error);
    res.status(500).json({ message: "Internal server error" });
  }
};

exports.sendMessage = async (req, res) => {
  try {
    const userId = req.header('X-User-Id');
    const { conversationId } = req.params;
    const { content } = req.body;

    if (!userId) {
      return res.status(400).json({ message: "User ID missing" });
    }

    const newMessage = new Message({
      sender: userId,
      receiver: conversationId,
      text: content
    });

    const savedMessage = await newMessage.save();
    

    res.status(201).json({
        id: savedMessage._id,
        sender: "me",
        content: savedMessage.text,
        time: savedMessage.createdAt
    });
  } catch (error) {
    console.error("Error sending message:", error);
    res.status(500).json({ message: "Internal server error" });
  }
};
