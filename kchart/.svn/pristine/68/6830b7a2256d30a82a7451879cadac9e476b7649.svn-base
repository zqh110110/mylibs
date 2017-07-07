import IDrawing from "./IDrawing.js"

export default class KLineDrawing extends IDrawing {
    constructor(render, name, option) {
        super(render, name, option)
            // Object.assign(KLineDrawing.prototype, new IDrawing)
    }

    preDraw(canvasCxt, render) {}

    onDraw(canvasCxt, render) {
        if (render.entitySet.length() == 0) {
            return
        }
        this.drawMaxminValue(canvasCxt, render)
        for (var i = 0; i < this.entitySet.length(); i++) {
            let entity = this.entitySet.getEntity(i)
            this.drawShadow(canvasCxt, render, entity, i + 1 /**- this.entitySet.startIndex + render.totalDx / render.unitX**/ )
            this.drawKline(canvasCxt, render, entity, i + 1 /**- this.entitySet.startIndex + render.totalDx / render.unitX**/ )
        }


    }

    drawEnd(canvasCxt, render) {

    }

    getOrder() {
        return 10;
    }

    drawMaxminValue(canvasCxt, render) {
        let maxEntity = render.entitySet.getMaxEntity()
        let minEntity = render.entitySet.getMinEntity()
        if (!maxEntity) {
            return
        }
        let option1 = maxEntity.option ? maxEntity.option : this.entitySet.option
        canvasCxt.fillStyle = option1.textColor
        canvasCxt.font = option1.font
        canvasCxt.save()
        canvasCxt.translate(render.getXPixel(render.entitySet.getMaxIndex() + 1), render.getYPixel(maxEntity.h))
        canvasCxt.rotate(Math.PI)
        let txt = ''
        let h = Number.parseFloat(maxEntity.h).toFixed(2)
        let l = Number.parseFloat(minEntity.l).toFixed(2)
        if (Math.abs((render.entitySet.getMaxIndex() + 1) - render.getX(0)) < 8) {
            canvasCxt.textAlign = "left"
            txt = `<-${h}`
        } else if (Math.abs(render.getX(render.drawViewPort.right) - (render.entitySet.getMaxIndex() + 1)) < 8) {
            canvasCxt.textAlign = "right"
            txt = `${h}->`
        } else {
            canvasCxt.textAlign = "right"
            txt = `${h}->`
        }
        canvasCxt.fillText(txt, 0, 0)
        canvasCxt.restore()

        canvasCxt.save()
        canvasCxt.translate(render.getXPixel(render.entitySet.getMinIndex() + 1), render.getYPixel(minEntity.l))
        canvasCxt.rotate(Math.PI)
        if (Math.abs((render.entitySet.getMinIndex() + 1) - render.getX(0)) < 8) {
            canvasCxt.textAlign = "left"
            txt = `<-${l}`
        } else if (Math.abs(render.getX(render.drawViewPort.right) - (render.entitySet.getMinIndex() + 1)) < 8) {
            canvasCxt.textAlign = "right"
            txt = `${l}->`
        } else {
            canvasCxt.textAlign = "left"
            txt = `<-${l}`
        }
        canvasCxt.fillText(txt, 0, 9)
        canvasCxt.restore()

        // canvasCxt.save()

        // let v1 = render.getY(0).toFixed(2)
        // let v2 = render.getY(render.drawViewPort.bottom * 1 / 3).toFixed(3)
        // let v3 = render.getY(render.drawViewPort.bottom * 2 / 3).toFixed(3)
        // let v4 = render.getY(render.drawViewPort.bottom - 20).toFixed(3)
        // canvasCxt.rotate(Math.PI)
        // canvasCxt.textAlign = "left"

        // canvasCxt.fillText(" " + v1, -1, 0)
        // canvasCxt.fillText(" " + v2, -1, -render.drawViewPort.bottom * 1 / 3)
        // canvasCxt.fillText(" " + v3, -1, -render.drawViewPort.bottom * 2 / 3)
        // canvasCxt.fillText(" " + v4, -1, -(render.drawViewPort.bottom - 20))
        // canvasCxt.restore()
    }

    drawShadow(canvasCxt, render, entity, index) {
        let option = entity.option ? entity.option : this.entitySet.option

        let strokeStyle = option.upLineColor
        if (Number.parseFloat(entity.o) >= Number.parseFloat(entity.c)) {
            strokeStyle = option.downLineColor
        } else {
            strokeStyle = option.upLineColor
        }

        canvasCxt.strokeStyle = strokeStyle
        canvasCxt.lineWidth = option.lineWidth
        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY
        canvasCxt.globalAlpha = option.globalAlpha
        let uh = entity.c
        let dh = entity.c
        if (Number.parseFloat(entity.o) >= Number.parseFloat(entity.c)) {
            uh = entity.o
            dh = entity.c
        } else {
            uh = entity.c
            dh = entity.o
        }
        canvasCxt.beginPath()

        let x = render.getXPixel(index)
        let dy = 0 //this.entitySet.getMinEntity().l
        canvasCxt.moveTo(x + this.halfvalue, render.getYPixel(entity.h - dy))
        canvasCxt.lineTo(x + this.halfvalue, render.getYPixel(uh - dy))
        canvasCxt.stroke()
        canvasCxt.moveTo(x + this.halfvalue, render.getYPixel(entity.l - dy))
        canvasCxt.lineTo(x + this.halfvalue, render.getYPixel(dh - dy))
        canvasCxt.stroke()
        canvasCxt.closePath()
    }

    drawKline(canvasCxt, render, entity, index) {
        let option = entity.option ? entity.option : this.entitySet.option

        let fillStyle = option.upColor
        let strokeStyle = option.upLineColor
        let isDown = Number.parseFloat(entity.o) >= Number.parseFloat(entity.c)
        if (isDown) {
            fillStyle = option.downColor
            strokeStyle = option.downLineColor
        } else {
            fillStyle = option.upColor
            strokeStyle = option.upLineColor
        }
        canvasCxt.beginPath()
        let x = render.getXPixel(index)
        let dy = 0 //this.entitySet.getMinEntity().l
        canvasCxt.lineWidth = option.strokeWidth
        canvasCxt.shadowBlur = option.shadowBlur
        canvasCxt.shadowColor = option.shadowColor
        canvasCxt.shadowOffsetX = option.shadowOffsetX
        canvasCxt.shadowOffsetY = option.shadowOffsetY
        canvasCxt.globalAlpha = option.globalAlpha
        if (option.solid && isDown) {
            canvasCxt.fillStyle = fillStyle
            let pix = 0
            if (Number.parseFloat(entity.c) == Number.parseFloat(entity.o)) {
                pix = 0.5
            }
            canvasCxt.fillRect(x + Math.ceil(render.option.klineWidth / 2), render.getYPixel(entity.o - dy), -option.klineWidth + this.halfvalue, render.getYPixel(entity.c) - render.getYPixel(entity.o) + pix)
        } else {
            canvasCxt.strokeStyle = strokeStyle
            canvasCxt.strokeRect(x + Math.ceil(render.option.klineWidth / 2) - option.strokeWidth + this.halfvalue, render.getYPixel(entity.o - dy), -option.klineWidth + 2, render.getYPixel(entity.c) - render.getYPixel(entity.o))
        }

        canvasCxt.closePath()
    }
}