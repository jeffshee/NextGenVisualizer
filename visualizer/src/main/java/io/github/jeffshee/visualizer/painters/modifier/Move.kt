package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import android.graphics.Paint
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Move : Painter {
    var painters: List<Painter>
    //
    var xR: Float
    var yR: Float

    override var paint = Paint()

    constructor(painters: List<Painter>, xR: Float = 0f, yR: Float = 0f) {
        this.painters = painters
        this.xR = xR
        this.yR = yR
    }

    constructor(painter: Painter, xR: Float = 0f, yR: Float = 0f) : this(
        listOf(painter), xR, yR
    )

    override fun calc(helper: VisualizerHelper) {
        painters.forEach { painter ->
            painter.calc(helper)
        }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.save()
        canvas.translate(canvas.width * xR, canvas.height * yR)
        painters.forEach { painter ->
            painter.paint.apply { colorFilter = paint.colorFilter;xfermode = paint.xfermode }
            painter.draw(canvas, helper)
        }
        canvas.restore()
    }
}