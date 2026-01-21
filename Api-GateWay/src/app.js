
const cookieParser = require('cookie-parser');
const express = require('express');
const app = express();
app.use(express.json());
app.use(cookieParser());
const {createProxyMiddleware}=require('http-proxy-middleware');
    const  expressRateLimit = require('express-rate-limit');
   const rateLimiter= expressRateLimit({
      windowMs:10*60*1000,
      max:100,
      message:"Too many requests from this IP, please try again after 10 minutes"
   });
   
app.use(rateLimiter);

app.use((req,res,next)=>{
      try{
            const token=req.cookies.token||req.headers?.authorization?.split(' ')[1];
            if(!token){
                  return res.status(401).json({message:"Unauthorized"});
            }
            next();

      }catch(err){
            console.error(err);
            return res.status(500).json({message:"Internal Server Error"});
      }
})


app.use('/service1',createProxyMiddleware({
      target:'http://localhost:3001',
      changeOrigin:true,    
}));

app.use('/service2',createProxyMiddleware({
      target:'http://localhost:3002',
      changeOrigin:true,    
}));


app.use('/service3',createProxyMiddleware({
      target:'http://localhost:3003',
      changeOrigin:true,    
}));


module.exports = app;