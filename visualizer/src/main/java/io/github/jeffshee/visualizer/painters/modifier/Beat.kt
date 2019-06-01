package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Beat : Painter {

    var painters: List<Painter>
    var startHz: Int
    var endHz: Int
    var xR: Float
    var yR: Float
    var baseR: Float
    var beatAmpR: Float
    var peak: Float

    constructor(
        painters: List<Painter>, startHz: Int = 60, endHz: Int = 800, xR: Float = .5f, yR: Float = .5f,
        baseR: Float = 1f, beatAmpR: Float = 1f, peak: Float = 200f
    ) {
        this.painters = painters
        this.startHz = startHz
        this.endHz = endHz
        this.xR = xR
        this.yR = yR
        this.baseR = baseR
        this.beatAmpR = beatAmpR
        this.peak = peak
    }

    constructor(
        painter: Painter, startHz: Int = 60, endHz: Int = 800, xR: Float = .5f, yR: Float = .5f,
        baseR: Float = 1f, beatAmpR: Float = 1f, peak: Float = 200f
    ) : this(
        listOf(painter), startHz, endHz, xR, yR, baseR, beatAmpR, peak
    )

    private val energy = GravityModel(0f)

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.save()
        energy.update(helper.getFftMagnitudeRange(startHz, endHz).average().toFloat())
        val width = canvas.width * (baseR + energy.height / peak * beatAmpR)
        canvas.scale(
            width / canvas.width, width / canvas.width,
            canvas.width * xR, canvas.height * yR
        )
        painters.forEach { painter ->
            painter.draw(canvas, helper)
        }
        canvas.restore()
    }
}