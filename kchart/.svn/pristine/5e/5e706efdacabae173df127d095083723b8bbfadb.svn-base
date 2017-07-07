import BaseRender from "./BaseRender.js"
export default class TimeRender extends BaseRender {

    constructor(period, name, option) {
        super(name, option)
    }

    initUnit() {
        if (this.entitySet.length() <= 0) {
            return
        }
        let h = Number.parseFloat(this.entitySet.getMaxEntity().h)
        let l = Number.parseFloat(this.entitySet.getMinEntity().l)
        let tempUnitY = (this.drawViewPort.bottom - this.padding[1] - this.padding[3]) / (h - l)
        this.drawViewPortMax_H = h + this.padding[1] / tempUnitY
        this.drawViewPortMin_L = l - this.padding[3] / tempUnitY
        this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L
        this.unitX = this.drawViewPort.right / this.xAxisCount //pix/min
        this.unitY = this.dy / (this.drawViewPort.bottom) // unit/pix
    }

    initAxisCount() {
        this.xAxisCount = 240
        this.yAxisCount = 3
    }

    firstInitViewPortIndex() {
        this.entitySet.startIndex = 0
        this.entitySet.endIndex = this.entitySet.length()
    }

    firstInitPosition() {
        this.entitySet.totalDx = 0
    }

    addToEnd(e) {
        this.entitySet.push(e)
        this.firstInitViewPortIndex()
        this.refresh()
    }

    addArrayToEnd(arrays) {
        arrays.forEach(e => {
            this.entitySet.push(e)
        })
        this.firstInitViewPortIndex()
        this.refresh()
    }

    refresh() {
        // if (this.baseChart) {
        //     this.baseChart.invalidate()
        // }
    }

    // touchStart(event, startX, startY) {

    // }

    // touchMove(event, pageX, pageY, dx, dy) {

    // }

    // touchEnd(event) {

    // }


    minuteToTime(mint) {
        let h = Number.parseInt(mint / 60);
        let m = Number.parseInt(mint % 60);
        if (h < 10) {
            h = "0" + h
        }
        if (m < 10) {
            m = "0" + m
        }
        return [h, m]
    }

}