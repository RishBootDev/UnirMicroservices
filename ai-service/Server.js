const app=require('./src/app');
require('dotenv').config();
const initSocketServer=require('./src/sockets/socket-server');
const httpserver=require('http').createServer(app);
 
    initSocketServer(httpserver);


    httpserver.listen(process.env.PORT,()=>{
        console.log("Connected");
      });
  
  


