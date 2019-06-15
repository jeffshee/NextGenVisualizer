package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlin.math.*
import kotlin.random.Random

class Zoom : Painter {
    var painters: List<Painter>
    var xR: Float
    var yR: Float
    var zoomer: Zoomer

    constructor(painters: List<Painter>, xR: Float = .05f, yR: Float = .05f, zoomer: Zoomer = Zoomer()) {
        this.painters = painters
        this.xR = xR
        this.yR = yR
        this.zoomer = zoomer
    }

    constructor(painter: Painter, xR: Float = .05f, yR: Float = .05f, zoomer: Zoomer = Zoomer()) : this(
        listOf(painter), xR, yR, zoomer
    )

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        zoomer.update()
        canvas.save()
        canvas.scale(1f + .1f*zoomer.x,1f + .1f*zoomer.x, canvas.width/2f, canvas.height/2f)
        painters.forEach { painter ->
            painter.draw(canvas, helper)
        }
        canvas.restore()
    }

    class Zoomer {
        var x = 0f
        var v = .01f

        fun update() {
            if (abs(x) >= 1f) {
                v = -v
            }
            x += v
        }

    }
}