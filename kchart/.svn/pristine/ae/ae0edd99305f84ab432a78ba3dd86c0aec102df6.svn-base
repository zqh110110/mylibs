import IDrawing from "./IDrawing.js"

export default class GridDrawing extends IDrawing {
    constructor(render, row, colum, axissum, name, option) {
        super(render, name, option)
        this.row = row ? row : 4
        this.colum = colum ? colum : 3
        this.axissum = axissum ? axissum : 2
    }

    preDraw(canvasCxt, render) {

    }

    onDraw(canvasCxt, render) {
        this.drawGrid(canvasCxt, render)
        this.drawAxisValues(canvasCxt, render)
            // canvasCxt.fillStyle = "red"
            // canvasCxt.fillRect(0, 0, -10, 100)
    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return -11;
    }

    drawGrid(canvasCxt, render) {
        canvasCxt.beginPath()
        canvasCxt.strokeStyle = "#f2f2f3"
        canvasCxt.lineWidth = 1

        for (let i = 0; i < this.row + 1; i++) {
            let y = Math.ceil((render.drawViewPort.bottom - (render.padding[1] + render.padding[3])) / this.row) * i + render.padding[3] + this.halfvalue
            canvasCxt.moveTo(0, y)
            canvasCxt.lineTo(-render.drawViewPort.right, y)
            canvasCxt.stroke()
        }

        for (let i = 0; i < this.colum + 1; i++) {
            let x = Math.ceil((render.drawViewPort.right - (render.padding[0] + render.padding[2])) / this.colum) * i + render.padding[0] + this.halfvalue
            canvasCxt.moveTo(-x, render.padding[3])
            canvasCxt.lineTo(-x, render.drawViewPort.bottom - render.padding[1])
            canvasCxt.stroke()
        }

        canvasCxt.closePath()
    }

    drawAxisValues(canvasCxt, render) {
        canvasCxt.save()
        canvasCxt.rotate(Math.PI)
        canvasCxt.textAlign = "left"
        canvasCxt.font = "normal 12px verdana"
        for (let i = 0; i < this.axissum + 1; i++) {
            if (this.axissum == 1 && i == 0) {
                continue
            }
            let y = Math.ceil((render.drawViewPort.bottom - (render.padding[1] + render.padding[3])) / this.axissum) * i + render.padding[3]
            let v = Math.abs(render.getY(y).toFixed(2))
                // if (i == 2) {
                //     console.log(`y = ${y} v = ${v}`)
                // }
            if (i == this.axissum) {
                y = y - 10
            }
            if (i != 0 && i != this.axissum) {
                y = y - 4
            }
            if (v > 10000) {
                v = (v / 10000).toFixed(2) + "万"
            }
            canvasCxt.fillText(" " + v, -1, -y)
        }
        canvasCxt.restore()
    }

}