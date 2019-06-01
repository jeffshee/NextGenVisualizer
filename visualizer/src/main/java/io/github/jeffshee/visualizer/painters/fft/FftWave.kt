package io.github.jeffshee.visualizer.painters.fft

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftWave(
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    },
    var startHz: Int = 0,
    var endHz: Int = 2000,
    var sliceNum: Int = 128,
    var interpolator: String = "sp",
    var side: String = "a",
    var mode: String = "",
    var xR: Float = 0f,
    var yR: Float = 1f,
    var wR: Float = 1f,
    var ampR: Float = 1f
) : Painter() {

    private val path = Path()
    private var points = Array(0) { GravityModel() }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(startHz, endHz)
        if (isQuiet(fft)) return

        val width = canvas.width.toFloat() * wR

        when (mode) {
            "mirror" -> {
                val mirrorFft = getMirrorFft(fft)
                if (points.size != mirrorFft.size) points =
                    Array(mirrorFft.size) { GravityModel(0f) }
                points.forEachIndexed { index, bar -> bar.update(mirrorFft[index].toFloat() * ampR) }
            }
            else -> {
                if (points.size != fft.size) points =
                    Array(fft.size) { GravityModel(0f) }
                points.forEachIndexed { index, bar -> bar.update(fft[index].toFloat() * ampR) }
            }
        }

        val psf = interpolateFft(points, sliceNum, interpolator)
        val sliceWidth = width / sliceNum
        if (paint.style == Paint.Style.STROKE) {
            path.moveTo(0f, -psf.value(0.0).toFloat())
            for (i in 1..sliceNum) {
                path.lineTo(sliceWidth * i, -psf.value(i.toDouble()).toFloat())
            }
        } else {
            path.moveTo(0f, 1f)
            for (i in 0..sliceNum) {
                path.lineTo(sliceWidth * i, -psf.value(i.toDouble()).toFloat())
            }
            path.lineTo(width, 1f)
            path.close()
        }
        drawHelper(canvas, side, xR, yR) { canvas.drawPath(path, paint) }
        path.reset()
    }
}