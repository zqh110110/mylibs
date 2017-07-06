import IDrawing from "./IDrawing.js"
import KLineRender from "./KLineRender.js"

export default class AvgDrawing extends IDrawing {
    constructor(render, type, perid, linecolor, linewidth, name, option) {
        super(render, name, option)
        this.type = type ? type : "c"
        this.perid = perid ? perid : 5
        this.linecolor = linecolor ? linecolor : "black"
        this.linewidth = linewidth ? linewidth : 1
    }

    preDraw(canvasCxt, render) {

    }

    onDraw(canvasCxt, render) {
        this.drawAvg(canvasCxt, render)
    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return 0;
    }

    drawAvg(canvasCxt, render) {
        canvasCxt.beginPath()
        let option = this.entitySet.option
        canvasCxt.strokeStyle = this.linecolor
        canvasCxt.lineWidth = this.linewidth
        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY
        let lt = false

        for (var i = 0; i < this.entitySet.length(); i++) {

            let ma_y = render.entitySet.getAVG(i, this.type, this.perid)
            if (lt) {
                canvasCxt.lineTo(render.getXPixel(i + 1) + render.halfvalue, render.getYPixel(ma_y))
            }
            if (!lt && ma_y != 0) {
                canvasCxt.moveTo(render.getXPixel(i + 1) + render.halfvalue, render.getYPixel(ma_y))
                lt = true
            }
        }
        canvasCxt.stroke()
        canvasCxt.closePath()
    }

    getAvgMaxValue() {
        if (this.render.entitySet.length() == 0) {
            return 0;
        }
        let max = this.render.entitySet.getAVG(0, this.type, this.perid)
        for (let i = this.render.entitySet.startIndex; i < this.render.entitySet.endIndex; i++) {
            let entityNext = this.render.entitySet.getAVG(i, this.type, this.perid)
            if (entityNext > max) {
                max = entityNext
            }
        }
        return max
    }

    getAvgMinValue() {
        if (this.render.entitySet.length() == 0) {
            return 0;
        }
        let min = this.render.entitySet.getAVG(0, this.type, this.perid)
        for (let i = this.render.entitySet.startIndex; i < this.render.entitySet.endIndex; i++) {
            let entityNext = this.render.entitySet.getAVG(i, this.type, this.perid)
            if (entityNext < min) {
                min = entityNext
            }
        }
        return min
    }

}