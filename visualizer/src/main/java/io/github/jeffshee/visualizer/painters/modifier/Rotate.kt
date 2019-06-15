package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Rotate : Painter {
    var painters: List<Painter>
    //
    var pxR: Float
    var pyR: Float
    //
    var rpm: Float
    var offset: Float

    constructor(painters: List<Painter>, pxR: Float = .5f, pyR: Float = .5f, rpm: Float = 1f, offset: Float = 0f) {
        this.painters = painters
        this.pxR = pxR
        this.pyR = pyR
        this.rpm = rpm
        this.offset = offset
    }

    constructor(painter: Painter, pxR: Float = .5f, pyR: Float = .5f, rpm: Float = 1f, offset: Float = 0f) : this(
        listOf(painter), pxR, pyR, rpm, offset
    )

    private var rot: Float = 0f

    override fun calc(helper: VisualizerHelper) {
        painters.forEach { painter ->
            painter.calc(helper)
        }
    }
    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        rotateHelper(canvas, rot + offset, pxR, pyR) {
            painters.forEach { painter ->
                painter.draw(canvas, helper)
            }
        }
        if (rpm != 0f) {
            rot += rpm / 10f
            rot %= 360f
        }
    }
}