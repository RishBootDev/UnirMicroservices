const agent=require('../tools/agent');
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
 
async function GetTopNews(req,res) {
  try{
    const {msg}=req.body;
    const token=req.token;
    const userId=req.user._id;
    const result = await agent.invoke(

      {
        messages: [
            new SystemMessage(`You have to fetch top posts with the help of tool TopPost for field ${msg} and give a summary of those posts in the final response.in news format.`),
            new HumanMessage(`
              You are an AI assistant with access to tools.
              userid:${userId}
              CRITICAL RULES for tool "longtermmemory":
              1. NEVER call this tool without ALL fields.
              2. You MUST ALWAYS send:
                 - mode
                 - text
                 - userId
              
              VALID FORMAT ONLY:
              
              {
                "mode": "retrieve",
                "text": "<user message or query>",
                "userId": "<same userId provided>"
              }
              
              OR
              
              {
                "mode": "store",
                "text": "<important user information>",
                "userId": "<same userId provided>"
              }
              If text is missing, DO NOT call the tool.
              If information is not important, DO NOT store.   `),
            new HumanMessage(msg)

        ] 
      },
      {
        metadata: {
          token    
        }
      }
    );

    res.status(200).json({
      msg: "Top news fetched successfully",
      data: result.messages[result.messages.length - 1].content
    });

  }
  catch(err){ 
    console.log(err);
    res.status(500).json({ msg: "Server error", error: err.message });
  }
}

async function testGenerate(req,res) {
  const {files}=req;
  console.log(files);
  const file=files[0];
   

   const prompt=`You are a professional technical interviewer and career coach.
You will receive:
1. A resume (PDF/DOC/Image/Text) uploaded by the user.
Your task is to:
1. Carefully analyze the resume and extract:
   - Primary field/domain (e.g., Software Development, Data Science, Web Development, AI/ML, Finance, etc.)
   - Core technical skills
   - Programming languages, frameworks, tools, and technologies
   - Projects, internships, work experience
   - Academic background and certifications
   - Strength areas and potential weak areas

2. Based strictly on the resume analysis:
   - Identify the user's most relevant job role(s)
   - Understand what an interviewer is most likely to focus on

3. Generate TOP 10 INTERVIEW QUESTIONS that:
   - Are highly relevant to the user’s field and skills
   - Are tailored to the exact technologies and projects mentioned in the resume
   - Include a mix of:
     - Conceptual questions
     - Practical / scenario-based questions
     - Project-based deep dive questions
     - Problem-solving or system thinking questions (if applicable)

4. For each question, provide:
   - The interview question
   - What the interviewer is trying to evaluate (concept/skill)
   - Difficulty level (Easy / Medium / Hard)

5. Maintain a professional interviewer tone.
6. Do NOT ask generic or unrelated questions.
7. Do NOT assume skills that are not present in the resume.
8. Output should be well-structured, clear, and concise.
9.Output the final response in the following JSON format ONLY:

Final Output Format:

 Resume Summary (Brief)

 Target Role(s)

 Top 10 Resume-Based Interview Questions
1. Question
   - Focus Area:
   - Difficulty:

(Repeat for all 10 questions)

 `
  try{
   let response= await main(prompt,file.buffer,file.mimetype);
   console.log("Raw response:", response);
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

 async function reviewQuestions(req,res) {
  const {files}=req;
  const file=files[0];
 // const {ques}=req.body;
 const ques=[
  {
      "Question": "Could you walk me through the overall architecture of your LeetCode Clone project, focusing on how different components like the frontend, backend, database, and external APIs (Judge0) interact to achieve real-time code execution and evaluation with a sub-2s response time?",
      "Focus Area": "System Design, Component Interaction, Real-time Architecture, Overall Project Understanding",
      "Difficulty": "Medium"
  },
  {
      "Question": "In your LeetCode Clone, you mentioned integrating Redis for caching and rate limiting to prevent API overuse and enhance server efficiency by 35%. Could you elaborate on the specific caching strategy you implemented (e.g., write-through, cache-aside) and the mechanism you used for rate limiting at 2000 req/sec, especially concerning potential race conditions?",
      "Focus Area": "Caching Strategies, Rate Limiting Implementation, Redis Practical Usage, Concurrency, Scalability",
      "Difficulty": "Medium"
  },
  {
      "Question": "You implemented JWT-based Authentication and Role-Based Authorization for over 10,000 user sessions. Can you explain the complete lifecycle of a JWT from user login to subsequent authenticated requests, and describe how Role-Based Authorization was enforced to protect specific REST API routes for actions like problem creation?",
      "Focus Area": "Authentication/Authorization Concepts, JWT Mechanics, API Security, Access Control",
      "Difficulty": "Medium"
  },
  {
      "Question": "For the LeetCode Clone, you optimized MongoDB schemas with compound indexing and query optimization, improving data retrieval time by 40%. Could you provide a concrete example of a challenging query you optimized, explain the indexing strategy you applied, and how you verified the performance improvement?",
      "Focus Area": "Database Indexing, Query Optimization, MongoDB Practical Skills, Performance Measurement",
      "Difficulty": "Medium"
  },
  {
      "Question": "The project features a responsive React.js frontend with a real-time code editor and submission tracker. How did you achieve this real-time communication between the React frontend and the Node.js/Express.js backend, and what specific technologies or patterns did you employ to ensure smooth UI updates and synchronization?",
      "Focus Area": "Real-time Communication (e.g., WebSockets), Frontend-Backend Interaction, React.js State Management, UI Responsiveness",
      "Difficulty": "Medium"
  },
  {
      "Question": "You integrated the Judge0 API to support 15+ programming languages. Describe the challenges you faced during this third-party API integration, particularly regarding handling diverse language submissions, managing asynchronous responses, and ensuring the reported 99.9% execution accuracy.",
      "Focus Area": "Third-party API Integration, Asynchronous Programming, Error Handling, Data Consistency, Reliability",
      "Difficulty": "Medium"
  },
  {
      "Question": "Consider a scenario where your LeetCode Clone experiences a sudden, sustained 10x increase in user traffic and code submissions. Beyond your current Redis implementation, what further architectural changes or additional services would you consider to ensure the system remains highly available, scalable, and performs within its sub-2s response time target?",
      "Focus Area": "Scalability, System Design, High Availability, Load Balancing, Microservices",
      "Difficulty": "Hard"
  },
  {
      "Question": "Node.js is known for its single-threaded, non-blocking I/O model. In your LeetCode Clone, how did you ensure that long-running tasks, such as external calls to the Judge0 API for code execution, did not block the Node.js event loop and negatively impact the sub-2s response time for other user requests?",
      "Focus Area": "Node.js Event Loop, Asynchronous Programming, Concurrency, Process Management, Performance Optimization",
      "Difficulty": "Hard"
  },
  {
      "Question": "You have listed Generative AI (OpenAI API, LangChain.js integration) in your technical skills. While not explicitly detailed in your project, how might you envision integrating AI/ML capabilities into a platform like the LeetCode Clone to enhance the user experience, provide new features, or improve problem-solving assistance?",
      "Focus Area": "Application of AI/ML, Creative Problem Solving, Feature Ideation, Understanding of AI Use Cases",
      "Difficulty": "Medium"
  },
  {
      "Question": "Throughout the development of your LeetCode Clone, what was the most challenging technical problem you encountered? Describe your systematic approach to debugging and resolving it, and what key lessons you learned about robust software development from that experience.",
      "Focus Area": "Problem-Solving Methodology, Debugging Skills, Critical Thinking, Learning from Challenges, Resilience",
      "Difficulty": "Medium"
  }
];
   const prompt=`You are an expert evaluator and examiner.
You will be given:
1. An image containing handwritten or typed answers for questions:${ques}.
2. The image includes multiple answers written serial-wise
   (e.g., Q1, Q2, Q3…).
Your tasks are:

STEP 1: Image Understanding
- Carefully analyze the image.
- Identify each answer using its serial number (Q1, Q2, Q3, etc.).
- Extract the full content of every answer exactly as written.

STEP 2: Answer Analysis
For each extracted answer:
- Understand what question it is attempting to answer.
- Analyze:
  - Conceptual correctness
  - Completeness
  - Clarity of explanation
  - Logical flow
  - Use of examples (if any)
- Identify missing points or misconceptions.

STEP 3: Scoring (IMPORTANT)
- Assign a score to each answer out of 10.
- Clearly justify the score in 1–2 lines.

STEP 4: Overall Evaluation
- Calculate the total score.
- Calculate the percentage.
- Give an overall performance rating:
  - Excellent / Good / Average / Needs Improvement
- Mention strong areas and weak areas.

RULES:
- Maintain strict serial-wise order.
- Do NOT merge answers.
- Do NOT assume content not visible in the image.
- Be fair, objective, and academic in evaluation.
- If any answer is partially unreadable, mention it explicitly.
- Output the final response in the following JSON format ONLY:

OUTPUT FORMAT (STRICT):

 Extracted Answers
Q1:
<answer text>

Q2:
<answer text>

---
 Evaluation & Scoring
Q1:
- Analysis:
- Score: X/10
Q2:
- Analysis:
- Score: X/10
---
 Final Result
- Total Score: XX / (N × 10)
- Percentage: XX%
- Overall Rating:
- Strengths:
- Improvements Needed:
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
    PostCaption,
    GetTopNews,
    testGenerate,
    reviewQuestions
}