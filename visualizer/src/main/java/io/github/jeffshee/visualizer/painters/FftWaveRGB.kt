package io.github.jeffshee.visualizer.painters

import android.graphics.*
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftWaveRGB(private val paint: Paint) : Painter() {
    // TODO setting RGB Color doesn't work
    var startHzR = 0
    var endHzR = 1000
    var startHzG = 400
    var endHzG = 1500
    var startHzB = 800
    var endHzB = 2000
    var colorR = Color.RED
    var colorG = Color.GREEN
    var colorB = Color.BLUE
    var interpolator = "sp"

    private val waveR = FftWave(Paint(paint.flags).apply {
        style = Paint.Style.FILL;color = colorR;xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }).apply { startHz = startHzR;endHz = endHzR;interpolator = this@FftWaveRGB.interpolator }
    private val waveG = FftWave(Paint(paint.flags).apply {
        style = Paint.Style.FILL;color = colorG;xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }).apply { startHz = startHzG;endHz = endHzG;interpolator = this@FftWaveRGB.interpolator }
    private val waveB = FftWave(Paint(paint.flags).apply {
        style = Paint.Style.FILL;color = colorB;xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }).apply { startHz = startHzB;endHz = endHzB;interpolator = this@FftWaveRGB.interpolator }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        waveR.draw(canvas, helper)
        waveG.draw(canvas, helper)
        waveB.draw(canvas, helper)
    }
}