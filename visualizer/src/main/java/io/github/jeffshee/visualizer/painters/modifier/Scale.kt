package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Scale : Painter {
    var painters: List<Painter>
    var wR: Float
    var hR: Float
    var pxR: Float
    var pyR: Float

    constructor(painters: List<Painter>, wR: Float = 1f, hR: Float = 1f, pxR: Float = .5f, pyR: Float = .5f) {
        this.painters = painters
        this.wR = wR
        this.hR = hR
        this.pxR = pxR
        this.pyR = pyR

    }

    constructor(painter: Painter, wR: Float = 1f, hR: Float = 1f, pxR: Float = .5f, pyR: Float = .5f) : this(
        listOf(painter), wR, hR, pxR, pyR
    )

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.save()
        canvas.scale(wR, hR, pxR * canvas.width, pyR * canvas.height)
        painters.forEach { painter ->
            painter.draw(canvas, helper)
        }
        canvas.restore()
    }
}