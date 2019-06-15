package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlin.math.*
import kotlin.random.Random

class Shake : Painter {
    var painters: List<Painter>
    var xR: Float
    var yR: Float
    var shaker: Shaker

    constructor(painters: List<Painter>, xR: Float = .05f, yR: Float = .05f, shaker: Shaker = Shaker()) {
        this.painters = painters
        this.xR = xR
        this.yR = yR
        this.shaker = shaker
    }

    constructor(painter: Painter, xR: Float = .05f, yR: Float = .05f, shaker: Shaker = Shaker()) : this(
        listOf(painter), xR, yR, shaker
    )

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        shaker.update()
        drawHelper(canvas, "a", xR * shaker.x, yR * shaker.y) {
            painters.forEach { painter ->
                painter.draw(canvas, helper)
            }
        }
    }

    class Shaker {
        var x = 0f
        var y = 0f
        var v = .01f
        var theta = 0f

        private val range = toRad(60f)
        private val pi = PI.toFloat()

        fun update() {
            if (x * x + y * y >= 1f) {
                theta = toCenter() + range * Random.nextFloat() - range / 2f
            }
            x += v * cos(theta)
            y += v * sin(theta)
        }

        private fun toCenter(): Float {
            return pi + atan2(y, x)
        }

        private fun toRad(deg: Float): Float {
            return deg / 180f * PI.toFloat()
        }

    }
}