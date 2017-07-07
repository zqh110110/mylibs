import BaseRender from "./BaseRender.js"
import Options from "./Options.js"

export default class KLineRender extends BaseRender {

    constructor(name, option) {
        super(name, option)
    }

    zoomIn() {
        let zoom = this.option.klineWidth / 2
        if (zoom < 4) {
            zoom = 4
        }
        let dx = (this.option.klineWidth - zoom) * this.entitySet.endIndex
        this.entitySet.totalDx = this.entitySet.totalDx + dx
        this.option.klineWidth = zoom
        this.baseChart.invalidate()
        this.baseChart.invalidate()
    }

    zoomOut() {
        let zoom = this.option.klineWidth * 2
        if (zoom > 32) {
            zoom = 32
        }
        let dx = (this.option.klineWidth - zoom) * this.entitySet.endIndex
        this.entitySet.totalDx = this.entitySet.totalDx + dx
        this.option.klineWidth = zoom
        this.baseChart.invalidate()
        this.baseChart.invalidate()
    }

    zoomDefault() {
        let dx = (new Options().klineWidth - this.option.klineWidth) * this.entitySet.endIndex
        this.entitySet.totalDx = this.entitySet.totalDx + dx
        this.option.klineWidth = new Options().klineWidth
        this.baseChart.invalidate()
        this.baseChart.invalidate()
    }
}