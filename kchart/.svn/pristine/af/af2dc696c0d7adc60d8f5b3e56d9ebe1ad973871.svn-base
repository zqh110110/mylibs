import BaseRender from "./BaseRender.js"
export default class IndexRender extends BaseRender {

    constructor(name, option) {
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
        this.unitX = this.entitySet.option.klineWidth + this.entitySet.option.klineGap
        this.unitY = this.dy / (this.drawViewPort.bottom)
            // console.log(`h = ${h}  l = ${l} drawViewPortMax_H = ${this.drawViewPortMax_H} drawViewPortMin_L = ${this.drawViewPortMin_L} unitX = ${this.unitX} unitY = ${this.unitY}`)
    }

}