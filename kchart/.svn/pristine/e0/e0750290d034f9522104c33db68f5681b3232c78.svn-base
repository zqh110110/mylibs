export default class IDrawing {

    constructor(render, name, option) {
        if (render) {
            this.entitySet = render.entitySet
            this.render = render
            this.halfvalue = render.halfvalue
            this.render.addDrawing(this)
        }

    }

    preDraw(canvasCxt, render) {
        // this.render.clearRender()
        canvasCxt.save()
    }

    onDraw(canvasCxt, render) {

    }

    drawEnd(canvasCxt, render) {
        canvasCxt.restore()
    }

    getOrder() {
        return 0;
    }

}