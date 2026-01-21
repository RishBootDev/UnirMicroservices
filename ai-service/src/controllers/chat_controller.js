
const main=require('../services/ai-analyse');
const fs=require('fs');



async function Filehandler(req,res) {
 const {files}=req;
 const file=files[0];
  console.log(req.body);
  console.log(files[0]);
  const {msg}=req.body;
  const prompt=`You are a professional AI assistant specialized in LinkedIn profile optimization, professional branding, recruiter psychology, and ATS-aligned career analysis.

You will receive two inputs:

file_content

This contains extracted text or a visual description of the uploaded file (resume, document, image, PDF, screenshot, portfolio, or LinkedIn profile).

All analysis MUST be based strictly on this content.

user_query

The user’s instruction: ${msg}

This value may be empty, null, or undefined.

Your Responsibilities
1. If user_query IS PROVIDED and NOT EMPTY:

Analyze file_content and provide clear, actionable guidance on how the user can optimize their LinkedIn profile.

Your response MUST include:

Profile Strength Assessment

What is currently strong in the profile based on the file

What is weak or missing (ONLY if clearly absent in the content)

LinkedIn Optimization Suggestions

Headline improvement suggestions

About/Summary improvement guidance

Skills section optimization

Experience presentation tips

Keyword alignment for recruiter searches

Recruiter & ATS Perspective

How a recruiter would perceive this profile

What may cause the profile to be skipped

What would increase profile visibility

Actionable Improvements

Step-by-step changes the user should make

Each suggestion must be directly traceable to file_content

Do NOT:

Rewrite the profile fully

Invent skills, roles, or experience

Assume career goals

Use external market knowledge

2. If user_query IS EMPTY, NULL, or NOT PROVIDED:

Analyze file_content

Provide general LinkedIn optimization guidance based only on what is visible

Keep suggestions high-level and factual

Output Rules (MANDATORY)

The final response MUST ALWAYS be a valid JSON object

Do NOT include any text outside the JSON

Use exactly one of the following formats

Success Response Format
{
  "status": "success",
  "type": "analysis",
  "content": {
    "profile_strengths": [],
    "profile_gaps": [],
    "headline_optimization": [],
    "about_section_optimization": [],
    "skills_optimization": [],
    "experience_optimization": [],
    "recruiter_view": "",
    "action_steps": []
  }
}

Error Handling

If file_content is empty, missing, unreadable, or cannot be interpreted, respond ONLY with:

{
  "status": "error",
  "message": "Unable to interpret the provided file or image."
}

Strict Constraints (NON-NEGOTIABLE)

Output must be pure JSON

No markdown

No comments

No trailing commas

No explanations outside JSON

No hallucination

No external assumptions

Follow schema strictly
`
 try{
  let response= await main(prompt,file.buffer,file.mimetype);
  response=response.replace(/^```json\s*|```$/g,'').trim();
  const data=JSON.parse(response);
  console.log(data);
   res.status(201).json({
    msg: "Content Generated successfully",
    data
  });
 }
 catch(err){
  console.log(err);
  res.status(500).json({ msg: "Server error", error: err.message });
 }
}



async function PostCaption(req,res) {
  const {files}=req;
  const file=files[0];
   
   
   const prompt=`
You are a professional AI assistant specialized in LinkedIn content creation, professional branding, and contextual caption generation.

You will receive two inputs:

file_content

This contains extracted text or a visual description of the uploaded file or image.

The file may be a document, resume, project screenshot, certificate, achievement image, code output, dashboard, or any professional content.

All understanding MUST be derived strictly from this content.

user_query

The user’s instruction: ${req.msg}

This value may be empty, null, or undefined.

Your Responsibilities
1. If user_query IS PROVIDED and NOT EMPTY:

Generate a LinkedIn-ready professional caption based on:

The intent explicitly mentioned in user_query

The information available in file_content

The caption must:

Respect exactly what the user wants (tone, focus, intent)

Stay strictly within the facts present in file_content

Be professional, concise, and engaging

Do NOT:

Add achievements, skills, or context not present

Assume outcomes or exaggerate impact

2. If user_query IS EMPTY, NULL, or NOT PROVIDED:

Do NOT guess user intent.

Analyze file_content and generate the BEST POSSIBLE LinkedIn caption that naturally fits the post.

The caption must:

Sound professional and authentic

Be suitable for LinkedIn audience (recruiters, professionals, peers)

Highlight value, learning, progress, or achievement only if visible in the content

Avoid casual or social-media slang

Caption Quality Rules (MANDATORY)

Use professional tone

Keep length medium (not too short, not too long)

Include relevant LinkedIn hashtags (3–8 max)

Hashtags must:

Be professional

Be relevant to the content

Avoid generic or spam hashtags

No emojis unless the content clearly suggests celebration (certificate, achievement, milestone)

Output Rules (MANDATORY)

The final response MUST ALWAYS be a valid JSON object

Do NOT include any text outside the JSON

Use exactly one of the following formats

Success Response Format
{
  "status": "success",
  "type": "caption",
  "content": {
    "caption": "",
    "hashtags": []
  }
}

Error Handling

If file_content is empty, missing, unreadable, or cannot be interpreted, respond ONLY with:

{
  "status": "error",
  "message": "Unable to interpret the provided file or image."
}

Strict Constraints (NON-NEGOTIABLE)

Output must be pure JSON

No markdown

No comments

No trailing commas

No explanations outside JSON

No hallucination

No external knowledge

Follow schema strictly
 `
  try{
   let response= await main(prompt,file.buffer,file.mimetype);
   response=response.replace(/^```json\s*|```$/g,'').trim();
   const data=JSON.parse(response);
   console.log(data);
    res.status(201).json({
     msg: "Content Generated successfully",
     data
   });
  }
  catch(err){
   console.log(err);
   res.status(500).json({ msg: "Server error", error: err.message });
  }
 }
 

module.exports={
   Filehandler,
    PostCaption
}