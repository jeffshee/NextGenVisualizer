package io.github.jeffshee.visualizer.painters.modifier

import android.graphics.Canvas
import android.graphics.Paint
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Beat : Painter {
    var painters: List<Painter>
    //
    var startHz: Int
    var endHz: Int
    //
    var pxR: Float
    var pyR: Float
    //
    var radiusR: Float
    var beatAmpR: Float
    var peak: Float

    override var paint = Paint()

    constructor(
        painters: List<Painter>, startHz: Int = 60, endHz: Int = 800, pxR: Float = .5f, pyR: Float = .5f,
        radiusR: Float = 1f, beatAmpR: Float = 1f, peak: Float = 200f
    ) {
        this.painters = painters
        this.startHz = startHz
        this.endHz = endHz
        this.pxR = pxR
        this.pyR = pyR
        this.radiusR = radiusR
        this.beatAmpR = beatAmpR
        this.peak = peak
    }

    constructor(
        painter: Painter, startHz: Int = 60, endHz: Int = 800, pxR: Float = .5f, pyR: Float = .5f,
        radiusR: Float = 1f, beatAmpR: Float = 1f, peak: Float = 200f
    ) : this(
        listOf(painter), startHz, endHz, pxR, pyR, radiusR, beatAmpR, peak
    )

    private val energy = GravityModel(0f)

    override fun calc(helper: VisualizerHelper) {
        energy.update(helper.getFftMagnitudeRange(startHz, endHz).average().toFloat())
        painters.forEach { painter ->
            painter.calc(helper)
        }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        canvas.save()
        val width = canvas.width * (radiusR + energy.height / peak * beatAmpR)
        canvas.scale(
            width / canvas.width, width / canvas.width,
            canvas.width * pxR, canvas.height * pyR
        )
        painters.forEach { painter ->
            painter.paint.apply { colorFilter = paint.colorFilter;xfermode = paint.xfermode }
            painter.draw(canvas, helper)
        }
        canvas.restore()
    }
}