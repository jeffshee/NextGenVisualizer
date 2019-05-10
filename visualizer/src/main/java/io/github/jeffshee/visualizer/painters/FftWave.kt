package io.github.jeffshee.visualizer.painters

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftWave(private val paint: Paint) : Painter() {
    var startHz = 0
    var endHz = 2000
    val sliceNum = 128
    var interpolator = "sp"

    private var width: Float = 0f
    private var height: Float = 0f
    private var points: Array<GravityModel>

    init {
        points = Array(0) { GravityModel(0f) }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(helper, startHz, endHz)
        if (isQuiet(fft)) return
        if (fft.size != points.size) points =
            Array(helper.hzToFftIndex(endHz) - helper.hzToFftIndex(startHz)) { GravityModel(0f) }
        points.forEachIndexed { index, bar -> bar.update(fft[index].toFloat()) }
        val psf = interpolateFftWave(points, sliceNum, interpolator)

        width = canvas.width.toFloat()
        height = canvas.height.toFloat()
        val sliceWidth = width / sliceNum
        val path = Path()
        path.moveTo(0f, height)
        for (i in 0..sliceNum) {
            path.lineTo(sliceWidth * i, height - psf.value(i.toDouble()).toFloat())
        }
        path.lineTo(width, height)
        path.close()
        canvas.drawPath(path, paint)
    }
}