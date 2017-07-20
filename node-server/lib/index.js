import express from "express"
import Q from "q"
import net from "net"
import hqConfig from "./hqConfig.js"

let client = net.connect({ port: hqConfig.port, host: hqConfig.ip }, () => {
    console.log("connected")
})

function reqHqData(params) {
    return Q.Promise(function(resolve, reject, notify) {

    })
}

let app = express()
let router = express.Router()

app.use("/", router)

router.get('/app', (req, res) => {
    res.send(`hello world! ${__dirname } aaa`)
})

app.listen(1337, () => {
    console.log('server running http://localhost:1337')
})