package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import android.graphics.Paint
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Move(
    //
    vararg val painters: Painter,
    //
    var xR: Float = 0f,
    var yR: Float = 0f
) : Painter() {

    override var paint = Paint()

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