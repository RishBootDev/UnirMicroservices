const express=require('express');
const router=express.Router();
const {authUser}=require('../middlewares/auth-middleware');
const {Filehandler,PostCaption}=require('../controllers/chat_controller');
const multer=require('multer');

const storage =multer.memoryStorage();
const upload=multer({storage, limits: { fileSize: 50 * 1024 * 1024 }});
router.post('/upload-files',upload.array("files"),Filehandler)
router.post('/generateCaption',upload.array("files"),PostCaption)
module.exports=router;