package io.github.jeffshee.visualizer.painters.fft

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftBar(
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
    var gapX: Float = 0f,
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

        val barWidth = (width - (barNum + 1) * gapX) / barNum
        bars.forEachIndexed { index, bar -> bar.update(psf.value(index.toDouble()).toFloat() * ampR) }
        drawHelper(canvas, side, xR, yR) {
            bars.forEachIndexed { index, bar ->
                canvas.drawRect(
                    barWidth * index + gapX * (index + 1), -bar.height,
                    barWidth * (index + 1) + gapX * (index + 1), 0f,
                    paint
                )
            }
        }
    }
}