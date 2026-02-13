require("dotenv").config();
const { Eureka } = require("eureka-js-client");
const app = require('./src/app');
const http = require("http");
const { Server } = require("socket.io");
const connectDB = require('./src/db/db');

const server = http.createServer(app);

const io = new Server(server, {
  cors: { origin: "*" }
});

require("./src/socket.io.js/socket")(io);

connectDB();

const PORT = process.env.PORT || 5001; // Default to 5001 if not set

server.listen(PORT, () => {
  console.log("Server running on port", PORT);
});

// Eureka Configuration
const eurekaClient = new Eureka({
  instance: {
    app: "CHATSERVICE",
    instanceId: `CHATSERVICE:${PORT}`,
    hostName: "localhost",
    ipAddr: "127.0.0.1",
    statusPageUrl: `http://localhost:${PORT}/`,
    healthCheckUrl: `http://localhost:${PORT}/health`,
    port: {
      "$": PORT,
      "@enabled": true
    },
    vipAddress: "chatservice", // Changed from node-service to match application name usually
    dataCenterInfo: {
      "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
      name: "MyOwn"
    }
  },
  eureka: {
    host: "localhost",
    port: 8761,
    servicePath: "/eureka/apps/"
  }
});

eurekaClient.start(error => {
  if (error) {
    console.error("Eureka registration failed", error);
  } else {
    console.log("Chat Service registered with Eureka");
  }
});

process.on("SIGINT", () => {
  console.log("Shutting down...");
  eurekaClient.stop(() => {
    console.log("Deregistered from Eureka");
    server.close(() => process.exit());
  });
});