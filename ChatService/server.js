require("dotenv").config();
const app=require('./src/app');
const http = require("http");

const { Server } = require("socket.io");

const connectDB = require('./src/db/db');



const server = http.createServer(app);

const io = new Server(server, {
  cors: { origin: "*" }
});

require("./src/socket.io.js/socket")(io);

connectDB();

server.listen(process.env.PORT, () => {
  console.log("Server running on port", process.env.PORT);
});
 