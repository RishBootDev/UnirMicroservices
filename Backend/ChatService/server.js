require("dotenv").config();
const { Eureka } = require("eureka-js-client");
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


const PORT=process.env.PORT;













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

    vipAddress: "node-service",

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
    console.log("Node service registered with Eureka");
  }
});

process.on("SIGINT", () => {
  console.log("Shutting down...");
  eurekaClient.stop(() => {
    console.log("Deregistered from Eureka");
    server.close(() => process.exit());
  });
});
 