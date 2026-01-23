const { tool } = require("@langchain/core/tools");
const { z } = require("zod");
const axios = require("axios");


const TopPost = tool(
  async ({ field }, config) => {
    const token = config.metadata.token;

    const response = await axios.get(
      `dummyapi.io/data/api/user?limit=5&${field}=true`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return JSON.stringify(response.data);
  },
  {
    name: "TopPost",
    description: "Search Top Posts by field",
    schema: z.object({
      field:z.string().describe(`The field to search top posts for`),
    }),
  }
);

const descriptionofPost=tool(
  async (postIdarray, config) => {
    const token = config.metadata.token;

  },{
    name: "descriptionofPost",
    description: "Gives Description of Post",
    schema: z.object({
      postIdarray:z.array(z.string()).describe(`The  array of ID of the Post for which description to be find`)
    }),
  }
)

const TopNews = tool(
  async (config) => {

 const res=   await axios.get(
      `https://newsapi.org/v2/everything?q=(%22software%20development%22%20OR%20%22technology%20industry%22%20OR%20%22AI%20industry%22%20OR%20%22startup%20technology%22)&sources=techcrunch,the-verge,wired,ars-technica&language=en&sortBy=publishedAt&pageSize=10&apiKey=0e0fac0115ca4157b70faf01e30adb1f`
    );
      //console.log(res.data.articles)
      const news=[];
    for(let i=0;i<=10;i++){
      news.push(res.data?.articles[i]?.description||"NOT FOUND");
    }
   
    return  JSON.stringify(news);
  },
  {
    name: "TopNews",
    description: "Gives Top News for Linkdln posts",
    schema: z.object({
      city:z.string().describe(`The Name of the City for which News to be find`)
    }),
  }
);

module.exports = {
  TopPost,
  descriptionofPost,
  TopNews,
};
