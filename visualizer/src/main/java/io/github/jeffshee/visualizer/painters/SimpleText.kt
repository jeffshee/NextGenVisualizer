package io.github.jeffshee.visualizer.painters

import android.graphics.Canvas
import android.graphics.Paint
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class SimpleText(private val paint: Paint) : Painter() {
    var text = ""
    var x = 100f
    var y = 100f

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.drawText(text, x, y, paint)
    }
}