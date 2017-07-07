import Options from "./Options.js"

export default class EntitySet {

    constructor(name, option) {
        this.entitys = new Array()
        this.maxIndex = 0
        this.minIndex = 0
        this.name = name
        this.option = option ? option : new Options()
        this.startIndex = 0
        this.endIndex = 0

        this.hightlightIndex = 0
        this.hightlightX = -101
        this.hightlightY = 240
        this.hightlightShow = false

        this.totalDx = 0
        this.totalDy = 0
    }

    push(e) {
        this.entitys.push(e)
    }

    unshift(e) {
        this.entitys.unshift(e)
    }

    clear() {
        this.entitys.splice(0)
    }

    length() {
        return this.entitys.length
    }

    getEntity(index) {
        return this.entitys[index]
    }

    getMaxIndex() {
        this.maxIndex = this.startIndex
        let entity = this.getEntity(this.maxIndex)
        for (let i = this.startIndex; i < this.endIndex; i++) {
            let entityNext = this.getEntity(i)

            if (i != 0 && Number.parseFloat(entityNext.o) == 0) {
                let MINT = entityNext.MINT
                entityNext = this.getEntity(i - 1)
                entityNext.MINT = MINT
            }
            if (Number.parseFloat(entity.h) < Number.parseFloat(entityNext.h)) {
                this.maxIndex = i
                entity = entityNext
            }
        }
        return this.maxIndex
    }

    getMinIndex() {
        this.minIndex = this.startIndex
        let entity = this.getEntity(this.minIndex)
        for (let i = this.startIndex; i < this.endIndex; i++) {
            let entityNext = this.getEntity(i)

            if (i != 0 && Number.parseFloat(entityNext.o) == 0) {
                let preEntity = this.getEntity(i - 1)
                entityNext.o = preEntity.o
                entityNext.h = preEntity.h
                entityNext.l = preEntity.l
                entityNext.c = preEntity.c
                entityNext.v = preEntity.v
                entityNext.AMNT = preEntity.AMNT
                entityNext.AVG = preEntity.AVG
            }
            if (Number.parseFloat(entity.l) > Number.parseFloat(entityNext.l)) {
                this.minIndex = i
                entity = entityNext
            }
        }
        return this.minIndex
    }

    getMaxEntity() {
        return this.getEntity(this.getMaxIndex())
    }

    getMinEntity() {
        return this.getEntity(this.getMinIndex())
    }

    getMaxVolIndex() {
        this.maxIndex = this.startIndex
        let entity = this.getEntity(this.maxIndex)
        for (let i = this.startIndex; i < this.endIndex; i++) {
            let entityNext = this.getEntity(i)
            if (Number.parseFloat(entity.v) < Number.parseFloat(entityNext.v)) {
                this.maxIndex = i
                entity = entityNext
            }
        }
        return this.maxIndex
    }

    getMaxVolEntity() {
        return this.getEntity(this.getMaxVolIndex())
    }


    getAVG(index, type, perid) {
        if (!this.getEntity(index)) {
            return
        }
        let avg = 0
        switch (type) {
            case "c": //收盘价
                if (index < perid - 1) {
                    avg = 0
                } else {
                    for (let i = index - perid + 1; i <= index; i++) {
                        avg += Number.parseFloat(this.getEntity(i).c)
                    }
                    avg = avg / perid
                }
                break
            case "v": //量
                if (index < perid) {
                    avg = 0
                } else {
                    for (let i = index - perid; i < index; i++) {
                        avg += Number.parseFloat(this.getEntity(i).v)
                    }
                    avg = avg / perid
                }
                break
            case "n": //当前价

                avg = Number.parseFloat(this.getEntity(index).AVG)
                    // console.log(`avg = ${avg}`)
                break
        }
        return avg
    }

}