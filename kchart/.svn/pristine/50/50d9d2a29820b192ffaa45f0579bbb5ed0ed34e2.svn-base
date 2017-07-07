import KLineRender from "./KLineRender.js"
import BaseChart from "./BaseChart.js"
import KEntity from "./KEntity.js"
import KLineDrawing from "./KLineDrawing.js"
import datas from "./klinedata2.js"
import timedatas from "./timedata.js"
import IndexRender from "./IndexRender.js"
import VOLDrawing from "./VOLDrawing.js"
import AvgDrawing from "./AvgDrawing.js"
import TimeRender from "./TimeRender.js"
import TimeEntity from "./TimeEntity.js"
import TimeDrawing from "./TimeDrawing.js"
import TimeIndexRender from "./TimeIndexRender.js"
import CrossDrawing from "./CrossDrawing.js"
import { KXType, GetQueryString } from "./KXType.js"
import GridDrawing from "./GridDrawing.js"
import axios from "axios"
import jsonp from "jsonp"
import ajax from "./ajax.js"

import IO from "socket.io-client"

let chart = new BaseChart("#canvas");

let render = new KLineRender("kl")
    // render.margin = [10, 10, 20, 20]
render.viewport = { right: 1, bottom: 160 }
render.padding = [0, 20, 0, 20]
    // render.option.weight = 1

new KLineDrawing(render)
new CrossDrawing(render)
new AvgDrawing(render, "c", 5, "#508ce7", 1)
new AvgDrawing(render, "c", 10, "#ec9917", 1)
new AvgDrawing(render, "c", 20, "#e339dd", 1)
new AvgDrawing(render, "c", 60, "#21a764", 1)
new GridDrawing(render)

// let list = datas.LIST
// for (let entity of list) {
//     render.addToEnd(new KEntity(entity.OPEN, entity.HIGH, entity.LOW, entity.CLOS, entity.VOLM, entity.DATE))
// }
window.render = render

let indexRender = new IndexRender("index")
indexRender.viewport = { right: 1, bottom: 100 }
indexRender.margin = [0, 20, 0, 0]
indexRender.padding = [0, 10, 0, 0]
indexRender.entitySet = render.entitySet
new VOLDrawing(indexRender)
new CrossDrawing(indexRender).setCallBack((x, y, index, entity) => {
    console.log(`x = ${x} , y = ${y} , index = ${index} entity = o:${entity.o} c:${entity.c} h:${entity.h} l:${entity.l}`)
})
new AvgDrawing(indexRender, "v", 5, "#508ce7", 1)
new AvgDrawing(indexRender, "v", 10, "#ec9917", 1)
new GridDrawing(indexRender, 2, 3, 1)


let timeRender = new TimeRender("time")
timeRender.viewport = { right: 1, bottom: 150 }
timeRender.margin = [0, 20, 0, 0]
timeRender.padding = [0, 20, 0, 20]
new AvgDrawing(timeRender, "n", 1, "#ec9917", 1)
new GridDrawing(timeRender, 4, 2, 2)
new CrossDrawing(timeRender, "timeCross").setCallBack((x, y, index, entity) => {
    console.log(`x = ${x} , y = ${y} , index = ${index} entity = o:${entity.o} c:${entity.c} h:${entity.h} l:${entity.l}`)
})

let timeIndexRender = new TimeIndexRender("timeIndex")
timeIndexRender.viewport = { right: 1, bottom: 80 }
timeIndexRender.margin = [0, 0, 0, 0]
timeIndexRender.padding = [0, 10, 0, 0]
timeIndexRender.entitySet = timeRender.entitySet
new VOLDrawing(timeIndexRender, "timeline")
new CrossDrawing(timeIndexRender, "timeCross").setCallBack((x, y, index, entity) => {
    console.log(`x = ${x} , y = ${y} , index = ${index} entity = o:${entity.o} c:${entity.c} h:${entity.h} l:${entity.l}`)
})
new GridDrawing(timeIndexRender, 2, 2, 1)


// let times = timedatas.LIST
// for (let entity of times) {
//     timeRender.addToEnd(new TimeEntity(entity.MINT, entity.NOW, entity.VOLM, entity.AMNT, entity.AVG))
// }

new TimeDrawing(timeRender)

chart.addRender(render)
chart.addRender(indexRender)
chart.addRender(timeRender)
chart.addRender(timeIndexRender)
chart.resizeCanvas()
chart.render()

// let index = 0
// let hander = setInterval(function() {
//     let entity = times[index++]
//     timeRender.addToEnd(new TimeEntity(entity.MINT, entity.NOW, entity.VOLM, entity.AMNT, entity.AVG))
//     if (index >= 240) {
//         clearInterval(hander)
//     }
// }, 100)

// let socket = IO.connect("https://uatwebhq.shaomaicaibo.com:443")
// socket.on("connect", (resp) => {
//     console.log("client connected !")
//     socket.emit('addme', 'Gold{1234}Eqt', 'Gold1234{}');
//     socket.on('hqfunc_kx', function(para, data) {
//         window.d = data
//         window.p = para
//         let size = para.length
//         for (let i = 1; i < size; i++) {
//             let entity = para[i]
//             render.addToEnd(new KEntity(entity.jrkp, entity.zgcj, entity.zdcj, entity.zjcj, entity.cjsl))
//         }
//         chart.render()
//     })
//     let code = GetQueryString('code') //000001.1
//     socket.emit("request", 0x0002, { "code": code, "type": KXType.KX_WEEK, "count": 240 })
// })

// alert(GetQueryString('code'))
let code = GetQueryString('code')
axios.get(`http://192.168.10.194:8088/stock/k/day/${code}`, {
    transformResponse: data => {
        data = JSON.parse(data)
        let entitys = new Array()
        if (data && data[0] && data[0][2]) {
            data[0][2].forEach(function(e) {
                entitys.push(new KEntity(e[5], e[6], e[7], e[8], e[10] / 100, e[3]))
            });
            // let entity = entitys[entitys.length - 1]
            // entitys.push(new KEntity(entity.c, entity.c, entity.c, entity.c, 0, entity.t))
        }
        return entitys
    }
}).then(resp => {
    render.addArrayToEnd(resp.data)
    chart.render()
    startGetStockInfo()
})

function getStockInfo() {
    axios.get(`http://192.168.10.194:8088/stock/k/all/${code}`, {
        transformResponse: data => {
            data = JSON.parse(data)
            if (data && data[0]) {
                let lastEntity = new KEntity()
                let o = data[0][8]
                let v = data[0][14] / 100
                let h = data[0][9]
                let l = data[0][10]
                let c = data[0][11]
                let t = data[0][54]
                lastEntity.o = o
                lastEntity.h = h
                lastEntity.l = l
                lastEntity.c = c
                lastEntity.v = v
                lastEntity.t = t
                return lastEntity
            }
            return undefined
        }
    }).then(resp => {
        if (resp.data) {
            let lastEntity = render.entitySet.getEntity(render.entitySet.length() - 1)
            if (lastEntity.t != resp.data.t) {
                render.addToEnd(resp.data)
                chart.render()
            } else {
                lastEntity.o = resp.data.o
                lastEntity.h = resp.data.h
                lastEntity.l = resp.data.l
                lastEntity.c = resp.data.c
                lastEntity.v = resp.data.v
                lastEntity.t = resp.data.t
            }

            chart.invalidate()
        }
    })
}

function startGetStockInfo() {
    getStockInfo()
    let stockHandler = setInterval(function() {
        getStockInfo()

        // let date = new Date()
        // date = date.get + "-" + (date.getMonth() + 1) + "-" + date.getDate()
        // let threeTime = date + " 15:00:00"
        // threeTime = new Date(threeTime).getTime()
        // if (new Date().getTime() >= threeTime) {
        //     clearInterval(stockHandler)
        // }
    }, 3000)
}

function getFs() {
    axios.get(`http://192.168.10.194:8088/stock/k/fs/${code}`, {
        transformResponse: data => {
            data = JSON.parse(data)
            let entitys = new Array()
            if (data && data[0] && data[0][2]) {
                data[0][2].forEach(function(e, index) {
                    if (index >= 15) { //256 - 16 = 240
                        entitys.push(new TimeEntity(timeRender.minuteToTime(e[3]).join(":"), e[4], e[6], e[10], e[5]))
                    }
                });
            }
            return entitys
        }
    }).then(resp => {
        timeRender.entitySet.clear()
        timeRender.addArrayToEnd(resp.data)
        chart.invalidate()
    })
}

function startGetFs() {
    getFs()
    let hander = setInterval(function() {
        getFs()
        if (timeRender.entitySet.length() >= timeRender.xAxisCount) {
            clearInterval(hander)
        }
    }, 3000)
}
startGetFs()