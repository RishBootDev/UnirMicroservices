require("dotenv").config()
const http = require("http")
const app = require("./src/app")
const { initSocket } = require("./src/socket.io")

const server = http.createServer(app)

initSocket(server)

server.listen(process.env.PORT, () => {
  console.log(`Server running on port ${process.env.PORT}`)
})
