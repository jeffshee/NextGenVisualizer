package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Scale : Painter {
    var painters: List<Painter>
    //
    var scaleX: Float
    var scaleY: Float
    //
    var pxR: Float
    var pyR: Float

    constructor(painters: List<Painter>, scaleX: Float = 1f, scaleY: Float = 1f, pxR: Float = .5f, pyR: Float = .5f) {
        this.painters = painters
        this.scaleX = scaleX
        this.scaleY = scaleY
        this.pxR = pxR
        this.pyR = pyR

    }

    constructor(painter: Painter, scaleX: Float = 1f, scaleY: Float = 1f, pxR: Float = .5f, pyR: Float = .5f) : this(
        listOf(painter), scaleX, scaleY, pxR, pyR
    )

    override fun calc(helper: VisualizerHelper) {
        painters.forEach { painter ->
            painter.calc(helper)
        }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.save()
        canvas.scale(scaleX, scaleY, pxR * canvas.width, pyR * canvas.height)
        painters.forEach { painter ->
            painter.draw(canvas, helper)
        }
        canvas.restore()
    }
}