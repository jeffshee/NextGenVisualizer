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
    var ampR: Float = 1f,
    var enableBoost: Boolean = false
) : Painter() {

    private var points = Array(0) { GravityModel() }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        var fft = helper.getFftMagnitudeRange(startHz, endHz)
        if (isQuiet(fft)) return

        val width = canvas.width.toFloat() * wR
        if(enableBoost) fft = boost(fft)

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

        val psf = interpolateFft(points, barNum, interpolator)

        val barWidth = (width - (barNum + 1) * gapX) / barNum
        drawHelper(canvas, side, xR, yR) {
            for (i in 0 until barNum) {
                canvas.drawRect(
                    barWidth * i + gapX * (i + 1), -psf.value(i.toDouble()).toFloat(),
                    barWidth * (i + 1) + gapX * (i + 1), 0f,
                    paint
                )
            }
        }
    }
}