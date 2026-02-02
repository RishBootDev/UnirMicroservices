const { Server } = require("socket.io")
let io ;
const app = require("./app");
const initSocket = (server) => {
    io = new Server(server, {
      cors: {
        origin: "*"
      }
    })
  
  const onlineUsers = new Map()
  
  
  io.use((socket, next) => {
      const { id, name } = socket.handshake.auth
    
      if (!id) {
        return next(new Error("No user id"))
      }
    
      socket.user = { id, name }
      next()
    })
    
 
  io.on("connection", (socket) => {
    console.log("Socket.io server running")
    const userId = socket.user.id
  
    console.log("User connected:", userId)
    onlineUsers.set(userId, socket.id)
  

    socket.on("call:user", ({ receiverId }) => {
      const receiverSocket = onlineUsers.get(receiverId)
  
      if (!receiverSocket) {
        return socket.emit("call:user-offline")
      }
  
      io.to(receiverSocket).emit("call:incoming", {
        callerId: socket.user.id,
        callerName: socket.user.name
      })
    })
  
    
    socket.on("call:accept", ({ callerId }) => {
      const callerSocket = onlineUsers.get(callerId)
      if (callerSocket) {
        io.to(callerSocket).emit("call:accepted", {
          by: socket.user.id
        })
      }
    })
  
   
    socket.on("call:reject", ({ callerId }) => {
      const callerSocket = onlineUsers.get(callerId)
      if (callerSocket) {
        io.to(callerSocket).emit("call:rejected", {
          by: socket.user.id
        })
      }
    })
  
  
    socket.on("webrtc:offer", ({ to, offer }) => {
      const toSocket = onlineUsers.get(to)
      if (toSocket) {
        io.to(toSocket).emit("webrtc:offer", {
          from: socket.user.id,
          offer
        })
      }
    })
  
 
    socket.on("webrtc:answer", ({ to, answer }) => {
      const toSocket = onlineUsers.get(to)
      if (toSocket) {
        io.to(toSocket).emit("webrtc:answer", {
          from: socket.user.id,
          answer
        })
      }
    })
  

    socket.on("webrtc:ice", ({ to, candidate }) => {
      const toSocket = onlineUsers.get(to)
      if (toSocket) {
        io.to(toSocket).emit("webrtc:ice", {
          from: socket.user.id,
          candidate
        })
      }
    })
  
  
    socket.on("call:end", ({ to }) => {
      const toSocket = onlineUsers.get(to)
      if (toSocket) {
        io.to(toSocket).emit("call:ended", {
          by: socket.user.id
        })
      }
    })
  
  
    socket.on("disconnect", () => {
      console.log("User disconnected:", userId)
      onlineUsers.delete(userId)
    })
  })
  
  
  app.get("/ice-servers", (req, res) => {
    res.json({
      iceServers: [
        { urls: "stun:stun.l.google.com:19302" }
      ]
    })
  })

 
}
module.exports = { initSocket };