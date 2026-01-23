const app=require('./src/app');
require('dotenv').config();
const initSocketServer=require('./src/sockets/socket-server');
const httpserver=require('http').createServer(app);
 
    initSocketServer(httpserver);


    httpserver.listen(process.env.PORT,()=>{
        console.log("Connected");
      });


      const { Eureka } = require("eureka-js-client");
      
      
      const PORT =process.env.PORT
      
     
      
      const eurekaClient = new Eureka({
        instance: {
          app: "node-service",                  
          instanceId: `node-service:${PORT}`,
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
          port: 8711,
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

  
  


