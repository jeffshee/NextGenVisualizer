package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Rotate : Painter {
    var painters: List<Painter>
    var xR: Float
    var yR: Float
    var rpm: Float

    constructor(painters: List<Painter>, xR: Float = .5f, yR: Float = .5f, rpm: Float = 1f) {
        this.painters = painters
        this.xR = xR
        this.yR = yR
        this.rpm = rpm
    }

    constructor(painter: Painter, xR: Float = .5f, yR: Float = .5f, rpm: Float = 1f) : this(
        listOf(painter), xR, yR, rpm
    )

    private var rot: Float = 0f

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        rotateHelper(canvas, rot, xR, yR) {
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