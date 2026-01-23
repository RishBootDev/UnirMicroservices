const jwt = require("jsonwebtoken");
// const Message = require("./models/Message");

const onlineUsers = new Map(); 


module.exports = (io) => {

  io.use((socket, next) => {
    const token = socket.handshake.auth.token;
    if (!token) return next(new Error("No token"));

    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    socket.userId = decoded._id;
    next();
  });

  io.on("connection", (socket) => {
    console.log(socket.id);
    console.log("User connected:", socket.userId);

    onlineUsers.set(socket.userId, socket.id);

    
    socket.on("send_message", async ({ to, text }) => {

     
      const message = await Message.create({
        sender: socket.userId,
        receiver: to,
        text
      });

      
      const receiverSocketId = onlineUsers.get(to);
      if (receiverSocketId) {
        io.to(receiverSocketId).emit("receive_message", {
          from: socket.userId,
          text,
          createdAt: message.createdAt
        });
      }
    });

    socket.on("disconnect", () => {
      onlineUsers.delete(socket.userId);
      console.log("User disconnected:", socket.userId);
    });
  });
};
