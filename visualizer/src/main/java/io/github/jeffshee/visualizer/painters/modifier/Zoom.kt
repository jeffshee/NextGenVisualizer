package io.github.jeffshee.visualizer.painters.modifier

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.renderscript.Sampler
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Zoom : Painter {
    var painters: List<Painter>
    //
    var pxR: Float
    var pyR: Float
    //
    var anim: ValueAnimator

    constructor(
        painters: List<Painter>, pxR: Float = .5f, pyR: Float = .5f,
        anim: ValueAnimator = ValueAnimator.ofFloat(.9f, 1.1f).apply {
            duration = 8000;repeatCount = ValueAnimator.INFINITE;repeatMode = ValueAnimator.REVERSE
        }
    ) {
        this.painters = painters
        this.pxR = pxR
        this.pyR = pyR
        this.anim = anim
        anim.start()
    }

    constructor(
        painter: Painter, pxR: Float = .5f, pyR: Float = .5f,
        anim: ValueAnimator = ValueAnimator.ofFloat(.9f, 1.1f).apply {
            duration = 8000;repeatCount = ValueAnimator.INFINITE;repeatMode = ValueAnimator.REVERSE
        }
    ) : this(
        listOf(painter), pxR, pyR, anim
    )

    override fun calc(helper: VisualizerHelper) {
        painters.forEach { painter ->
            painter.calc(helper)
        }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.save()
        canvas.scale(
            anim.animatedValue as Float, anim.animatedValue as Float,
            pxR * canvas.width, pyR * canvas.height
        )
        painters.forEach { painter ->
            painter.draw(canvas, helper)
        }
        canvas.restore()
    }
}