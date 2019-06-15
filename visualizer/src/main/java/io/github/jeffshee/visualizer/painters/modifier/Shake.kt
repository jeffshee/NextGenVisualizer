package io.github.jeffshee.visualizer.painters.modifier

import android.animation.ValueAnimator
import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Shake : Painter {
    var painters: List<Painter>
    //
    var animX: ValueAnimator
    var animY: ValueAnimator

    constructor(
        painters: List<Painter>,
        animX: ValueAnimator = ValueAnimator.ofFloat(0f, .01f, 0f, -.01f, 0f).apply {
            duration = 16000;repeatCount = ValueAnimator.INFINITE
        },
        animY: ValueAnimator = ValueAnimator.ofFloat(0f, .01f, 0f, -.01f, 0f).apply {
            duration = 8000;repeatCount = ValueAnimator.INFINITE
        }
    ) {
        this.painters = painters
        this.animX = animX
        this.animY = animY
        animX.start()
        animY.start()
    }

    constructor(
        painter: Painter,
        animX: ValueAnimator = ValueAnimator.ofFloat(0f, .01f, 0f, -.01f, 0f).apply {
            duration = 16000;repeatCount = ValueAnimator.INFINITE
        },
        animY: ValueAnimator = ValueAnimator.ofFloat(0f, .01f, 0f, -.01f, 0f).apply {
            duration = 8000;repeatCount = ValueAnimator.INFINITE
        }
    ) : this(listOf(painter), animX, animY)


    override fun calc(helper: VisualizerHelper) {
        painters.forEach { painter ->
            painter.calc(helper)
        }

    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.save()
        drawHelper(canvas, "a", animX.animatedValue as Float, animY.animatedValue as Float) {
            painters.forEach { painter ->
                painter.draw(canvas, helper)
            }
        }
        canvas.restore()
    }
}