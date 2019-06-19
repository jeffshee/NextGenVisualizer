package io.github.jeffshee.visualizer.painters.misc

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class SimpleText(
    override var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE },
    var text: String = "",
    //
    var x: Float = 100f,
    var y: Float = 100f
    ) : Painter() {

    override fun calc(helper: VisualizerHelper) {
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.drawText(text, x, y, paint)
    }
}