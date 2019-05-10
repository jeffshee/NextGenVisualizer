package io.github.jeffshee.visualizer.painters.fft

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftLine(
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE;style = Paint.Style.STROKE;strokeWidth = 2f
    },
    var startHz: Int = 0,
    var endHz: Int = 2000,
    var barNum: Int = 128,
    var interpolator: String = "li",
    var side: String = "a",
    var mode: String = "",
    var xR: Float = 0f,
    var yR: Float = 1f,
    var wR: Float = 1f,
    var ampR: Float = 1f
) : Painter() {

    private val bars = Array(barNum) { GravityModel() }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(startHz, endHz)
        if (isQuiet(fft)) return

        val width = canvas.width.toFloat() * wR

        val psf = when (mode) {
            "mirror" -> {
                val mirrorFft = getMirrorFft(fft)
                interpolateFftBar(mirrorFft, barNum, interpolator)
            }
            else -> {
                interpolateFftBar(fft, barNum, interpolator)
            }
        }

        val barWidth = width / barNum
        val pts = FloatArray(4 * barNum)
        drawHelper(canvas, side, xR, yR, {
            bars.forEachIndexed { index, bar ->
                bar.update(psf.value(index.toDouble()).toFloat() * ampR)
                pts[4 * index] = barWidth * (index + .5f)
                pts[4 * index + 1] = -bar.height
                pts[4 * index + 2] = barWidth * (index + .5f)
                pts[4 * index + 3] = 0f
            }
            canvas.drawLines(pts, paint)
        }, {
            bars.forEachIndexed { index, bar ->
                bar.update(psf.value(index.toDouble()).toFloat() * ampR)
                pts[4 * index] = barWidth * (index + .5f)
                pts[4 * index + 1] = -bar.height
                pts[4 * index + 2] = barWidth * (index + .5f)
                pts[4 * index + 3] = bar.height
            }
            canvas.drawLines(pts, paint)
        })
    }
}