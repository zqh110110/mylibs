export default class Options {

    constructor(strokeColor, strokeWidth, solid, fillColor, upColor, downColor, upLineColor, downLineColor, kShadowColor, lineColor, lineWidth, klineWidth, klineGap, opacity, weight, background, shadowBlur, shadowColor, shadowOffsetX, shadowOffsetY, textColor, textFont, textPosition, textAlign, globalAlpha) {
        this.strokeColor = strokeColor ? strokeColor : '#000000'
        this.strokeWidth = strokeWidth ? strokeWidth : 1
        this.solid = solid ? solid : true
        this.fillColor = fillColor ? fillColor : '#ec3333'
        this.upColor = upColor ? upColor : '#ec3333'
        this.downColor = downColor ? downColor : '#0a9650'
        this.klineWidth = klineWidth ? klineWidth : 8
        this.klineGap = klineGap ? klineGap : 1
        this.lineColor = lineColor ? lineColor : '#508ce7'
        this.lineWidth = lineWidth ? lineWidth : 1
        this.upLineColor = upLineColor ? upLineColor : '#ec3333'
        this.downLineColor = downLineColor ? downLineColor : '#0a9650'
        this.opacity = opacity ? opacity : 1
        this.shadowBlur = shadowBlur ? shadowBlur : 0
        this.shadowColor = shadowColor ? shadowColor : 'gray'
        this.shadowOffsetX = shadowOffsetX ? shadowOffsetX : 0
        this.shadowOffsetY = shadowOffsetY ? shadowOffsetY : 0
        this.textColor = textColor ? textColor : '#333333'
        this.textFont = textFont ? textFont : 'bold 12px verdana'
        this.textPosition = textPosition ? textPosition : 'end' //inside, left, right, top, bottom
        this.textAlign = textAlign ? textAlign : 'center'
        this.kShadowColor = kShadowColor ? kShadowColor : '"black"'
        this.background = background ? background : '#ffffff'
        this.weight = weight ? weight : 0
        this.globalAlpha = globalAlpha ? globalAlpha : 0.8

    }

}