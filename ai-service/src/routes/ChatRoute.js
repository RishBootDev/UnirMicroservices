const express=require('express');
const router=express.Router();
const {authUser}=require('../middlewares/auth-middleware');
const chatController=require('../controllers/chat_controller');
const multer=require('multer');

const storage =multer.memoryStorage();
const upload=multer({storage, limits: { fileSize: 50 * 1024 * 1024 }});
router.post('/upload-files',authUser,upload.array("files"),chatController.Filehandler);
router.post('/generateCaption',upload.array("files"),chatController.PostCaption);
router.post('/topNews',authUser,chatController.GetTopNews);
router.post('/testGenerate',upload.array("files"),chatController.testGenerate);
router.post('/reviewQuestions',upload.array("files"),chatController.reviewQuestions);

module.exports=router;