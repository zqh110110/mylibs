import TimeRender from "./TimeRender.js"
export default class TimeIndexRender extends TimeRender {

    constructor(period, name, option) {
        super(name, option)
    }

    initUnit() {
        if (this.entitySet.length() <= 0) {
            return
        }
        let h = Number.parseFloat(this.entitySet.getMaxVolEntity().v)
        let l = 0
        let tempUnitY = (this.drawViewPort.bottom - this.padding[1] - this.padding[3]) / (h - l)
        this.drawViewPortMax_H = h + this.padding[1] / tempUnitY
        this.drawViewPortMin_L = l - this.padding[3] / tempUnitY
        this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L
        this.unitX = this.drawViewPort.right / this.xAxisCount //pix/min
        this.unitY = this.dy / (this.drawViewPort.bottom)

        this.entitySet.option.klineWidth = Math.ceil(this.unitX) - 1
        this.entitySet.option.klineGap = 1
    }

}