export default class BaseChart {

    constructor(canvasid) {
        this.canvas = document.querySelector(canvasid);
        this.canvasCxt = canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.left = this.canvas.offsetLeft;
        this.top = this.canvas.offsetTop;
        this.renders = new Set();
        this.orientation = "row" //row，col

        this.initTouch(this.canvas)
    }

    resizeCanvas(ratioW = 1, ratioH = 0) {
        let width = window.innerWidth * ratioW
        let height = 0
        if (ratioH != 0) {
            height = window.innerHeight * ratioH
        } else {
            this.renders.forEach(render => {
                height += render.viewport.bottom
            })
        }
        this.sawtooth(width, height)
    }

    sawtooth(width, height) {
        this.canvas.style.width = width + "px"
        this.canvas.style.height = height + "px"
        let ratio = window.devicePixelRatio
        if (ratio) {
            this.width = width
            this.height = height
            this.canvas.width = width * ratio
            this.canvas.height = height * ratio
            this.canvasCxt.scale(ratio, ratio)
        } else {
            this.width = width
            this.height = height
            this.canvas.width = width
            this.canvas.height = height
        }
    }

    initTouch(element) {
        element.addEventListener('touchstart', e => this.touch(e), false);
        element.addEventListener('touchmove', e => this.touch(e), false);
        element.addEventListener('touchend', e => this.touch(e), false);
        element.addEventListener('touchcancel', e => this.touch(e), false);
    }

    touch(e) {
        if (e && e.type == "touchmove") {
            e.preventDefault()
        }
        this.renders.forEach((render) => {
            render.touch(e)
        })
        this.invalidate()
    }

    addRender(render) {
        this.renders.add(render)
    }

    removeRender(render) {
        if (this.renders.has(render)) {
            this.renders.delete(render)
        }
    }

    clearRender() {
        this.renders.clear()
    }

    draw() {
        this.renders.forEach(render => {
            render.clearRender()
            this.canvasCxt.save()
            this.canvasCxt.rect(render.drawViewPort.left, render.drawViewPort.top, render.drawViewPort.right, render.drawViewPort.bottom)
            this.canvasCxt.clip();
            this.canvasCxt.save()
            this.canvasCxt.translate(render.drawViewPort.left, render.drawViewPort.top + render.drawViewPort.bottom)
            this.canvasCxt.rotate(Math.PI)
            render.drawings.forEach(drawing => {
                drawing.preDraw(this.canvasCxt, render)
                drawing.onDraw(this.canvasCxt, render)
                drawing.drawEnd(this.canvasCxt, render)
            })
            this.canvasCxt.restore()
            this.canvasCxt.restore()
        })
    }

    invalidate() {
        this.renders.forEach(render => {
            render.initAxisCount()
            render.initUnit()
            render.refreshStartEndIndex()
        })
        this.draw()
    }

    measure() {
        let weights = 0
        let totalSpace = 0
        let lastSpace = 0
        this.renders.forEach(render => {
            let weight = render.option.weight
            if (weight != 0) {
                weights += weight
            } else {
                if (this.orientation == 'row') {
                    if (render.viewport.bottom <= 1) {
                        render.viewport.bottom = this.height * render.viewport.bottom
                    }
                    totalSpace += render.viewport.bottom
                } else {
                    if (render.viewport.right <= 1) {
                        render.viewport.right = this.height * render.viewport.right
                    }
                    totalSpace += render.viewport.right
                }
            }
        })

        if (this.orientation == 'row') {
            this.height = Math.max(totalSpace, this.height)
            lastSpace = Math.abs(totalSpace - this.height)
        } else {
            this.width = Math.max(totalSpace, this.width)
            lastSpace = Math.abs(totalSpace - this.width)
        }

        this.renders.forEach(render => {
            let weight = render.option.weight
            if (this.orientation == 'row') {
                if (render.option.weight != 0) {
                    render.viewport.bottom = lastSpace * render.option.weight / weights
                    render.viewport.right = this.width
                } else {
                    if (render.viewport.bottom <= 1) {
                        render.viewport.bottom = this.height * render.viewport.bottom
                    }
                    if (render.viewport.right <= 1) {
                        render.viewport.right = this.width * render.viewport.right
                    }
                }
            } else {
                if (render.option.weight != 0) {
                    render.viewport.right = lastSpace * render.option.weight / weights
                    render.viewport.bottom = this.height
                } else {
                    if (render.viewport.right <= 1) {
                        render.viewport.right = this.width * render.viewport.right
                    }
                    if (render.viewport.bottom <= 1) {
                        render.viewport.bottom = this.height * render.viewport.bottom
                    }
                }
            }
        })
    }

    layout() {
        let lastX = 0
        let lastY = 0
        this.renders.forEach((render, index) => {
            render.viewport = { left: lastX, top: lastY, right: render.viewport.right, bottom: render.viewport.bottom }
            render.drawViewPort = { left: lastX + render.margin[0], top: lastY + render.margin[1], right: render.viewport.right - render.margin[0] - render.margin[2], bottom: render.viewport.bottom - render.margin[1] - render.margin[3] }
            if (this.orientation == 'row') {
                lastY = lastY + render.viewport.bottom
            } else {
                lastX = lastX + render.viewport.right
            }
        })

    }

    render() {
        this.measure()
        this.layout()
        this.renders.forEach(render => {
            render.ready()
            render.init(this)
        })
        this.draw()
    }
}