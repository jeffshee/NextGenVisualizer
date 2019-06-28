package io.github.jeffshee.visualizer.painters.misc

import android.graphics.*
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Gradient(
    override var paint: Paint = Paint()
) : Painter() {

    override fun calc(helper: VisualizerHelper) {
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        paint.shader = LinearGradient(
            0f,
            0f,
            0f,
            canvas.height / 2f,
            intArrayOf(Color.YELLOW, Color.RED),
            floatArrayOf(.9f, 1f),
            Shader.TileMode.CLAMP
        )
        canvas.drawPaint(paint)
    }
}
