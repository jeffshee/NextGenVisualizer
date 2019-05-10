package io.github.jeffshee.visualizer.painters

import android.graphics.Canvas
import android.graphics.Paint
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftBar(private val paint: Paint) : Painter() {
    var startHz = 0
    var endHz = 2000
    var barNum = 128
    var interpolator = "li"

    private var width: Float = 0f
    private var height: Float = 0f
    private var bars: Array<GravityModel> = Array(barNum) { GravityModel(0f) }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(helper, startHz, endHz)
        if (isQuiet(fft)) return
        val psf = interpolateFftBar(fft, barNum, interpolator)
        width = canvas.width.toFloat()
        height = canvas.height.toFloat()
        val barWidth = width / barNum
        bars.forEachIndexed { index, bar ->
            bar.update(psf.value(index.toDouble()).toFloat())
            canvas.drawRect(
                barWidth * index,
                height - bar.height,
                barWidth * (index + 1), height, paint
            )
        }
    }
}