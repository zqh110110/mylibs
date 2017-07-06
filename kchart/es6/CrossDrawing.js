import IDrawing from "./IDrawing.js"
import KLineRender from "./KLineRender.js"

export default class CrossDrawing extends IDrawing {
    constructor(render, type, linecolor, linewidth, name, option) {
        super(render, name, option)
        this.type = type ? type : "kline"
        this.linecolor = linecolor ? linecolor : "black"
        this.linewidth = linewidth ? linewidth : 0.5

        this.callback = undefined
    }

    preDraw(canvasCxt, render) {

    }

    onDraw(canvasCxt, render) {
        if (render.entitySet.hightlightShow) {
            this.drawCross(canvasCxt, render)
        }
    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return 40;
    }

    setCallBack(fn) {
        this.callback = fn
    }

    drawCross(canvasCxt, render) {
        canvasCxt.beginPath()
        let option = this.entitySet.option
        canvasCxt.strokeStyle = this.linecolor
        canvasCxt.lineWidth = this.linewidth
        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY

        let x = render.entitySet.hightlightX
        let index = render.getX(x)
        x = render.getXPixel(index)
        render.entitySet.hightlightIndex = x
        let yy1 = render.padding[3] + this.halfvalue
        let yy2 = (render.drawViewPort.bottom - (render.padding[1] + render.padding[3])) + render.padding[3] + this.halfvalue
        canvasCxt.moveTo(x + this.halfvalue, yy2)
        canvasCxt.lineTo(x + this.halfvalue, yy1)
        canvasCxt.stroke()

        let y = 0
        if (this.type.indexOf("time") != -1) {
            let entity = this.entitySet.getEntity(index)
            if (!entity) {
                return
            }
            y = render.getYPixel(entity.c)
        } else {
            y = render.drawViewPort.top + render.drawViewPort.bottom - render.entitySet.hightlightY
        }
        if (y >= yy1 && y <= yy2) {
            canvasCxt.moveTo(0, y + this.halfvalue)
            canvasCxt.lineTo(-render.drawViewPort.right, y + this.halfvalue)
            canvasCxt.stroke()

            let entity = this.entitySet.getEntity(index - 1)
            this.drawYtip(canvasCxt, render, x, y, index, entity)
            this.drawXtip(canvasCxt, render, x, y, index, entity)

            if (index <= this.entitySet.length()) {
                if (this.callback) {
                    this.callback(x, y, index, entity)
                }
            }
        }
        canvasCxt.closePath()

    }

    drawYtip(canvasCxt, render, x, y, index, entity) {

        let yy1 = render.padding[3] + this.halfvalue
        let yy2 = (render.drawViewPort.bottom - (render.padding[1] + render.padding[3])) + render.padding[3] + this.halfvalue
        if (-y >= yy1 && -y <= yy2) {
            return
        }

        let v = 0

        if (this.type.indexOf("time") != -1) {
            v = entity.c
        } else {
            v = Math.abs(render.getY(y).toFixed(2))
        }
        if (v > 10000) {
            v = (v / 10000).toFixed(2) + "万"
        } else {
            v = "" + v
        }
        canvasCxt.font = "normal 12px verdana"
        let txtWidth = canvasCxt.measureText(v).width
        let txtHeight = 18
        canvasCxt.save()
        canvasCxt.fillStyle = this.entitySet.option.background
        canvasCxt.fillRect(0, y - txtHeight / 2, -txtWidth, txtHeight)
        canvasCxt.restore()

        canvasCxt.save()
        canvasCxt.strokeStyle = this.linecolor
        canvasCxt.lineWidth = 1
        canvasCxt.strokeRect(-this.halfvalue, y - this.halfvalue - txtHeight / 2, -txtWidth, txtHeight)
        canvasCxt.restore()

        canvasCxt.save()
        canvasCxt.rotate(Math.PI)
        canvasCxt.textAlign = "left"
        canvasCxt.fillStyle = this.entitySet.option.textColor
        canvasCxt.font = "normal 12px verdana"
        canvasCxt.shadowBlur = 0
        canvasCxt.fillText(v, 0, -y + txtHeight / 4)
        canvasCxt.restore()
    }

    drawXtip(canvasCxt, render, x, y, index, entity) {
        if (!entity) {
            return
        }
        let v = entity.t + ""
        canvasCxt.font = "normal 12px verdana"
        let txtWidth = canvasCxt.measureText(v).width
        let txtHeight = 18
        let yy = (render.drawViewPort.bottom - (render.padding[1] + render.padding[3])) + render.padding[3] + this.halfvalue - txtHeight

        if (render.drawViewPort.right + x < txtWidth + 20) {
            x = x + txtWidth
        }

        canvasCxt.save()
        canvasCxt.fillStyle = this.entitySet.option.background
        canvasCxt.fillRect(x, yy, -txtWidth, txtHeight)
        canvasCxt.restore()

        canvasCxt.save()
        canvasCxt.strokeStyle = this.linecolor
        canvasCxt.lineWidth = 1
        canvasCxt.strokeRect(x + this.halfvalue, yy, -txtWidth, txtHeight)
        canvasCxt.restore()

        canvasCxt.save()
        canvasCxt.rotate(Math.PI)
        canvasCxt.textAlign = "left"
        canvasCxt.fillStyle = this.entitySet.option.textColor
        canvasCxt.font = "normal 12px verdana"
        canvasCxt.shadowBlur = 0
        canvasCxt.fillText(v, -x, -yy - txtHeight / 4)

        canvasCxt.restore()
    }


}