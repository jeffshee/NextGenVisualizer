package io.github.jeffshee.visualizer.painters

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Wave(private val paint: Paint) : Painter() {
    var startHz = 0
    var endHz = 2000
    var yR = 0.5f
    var sliceNum = 256
    private var width: Float = 0f
    private var height: Float = 0f

    @ExperimentalUnsignedTypes
    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        // TODO artifacts at the edge of the screen
        val fft = helper.getFftMagnitudeRange(startHz, endHz)
        if (isQuiet(fft)) return
        width = canvas.width.toFloat()
        height = canvas.height.toFloat()
        val wave = helper.getWave()
        val slice = wave.size / sliceNum
        val sliceWidth = width / sliceNum
        val path = Path()
        path.moveTo(0f, yR * height)
        for (i in 0 until sliceNum)
            path.lineTo(sliceWidth * i, yR * height - wave[slice * i].toUByte().toInt() + 128)
        path.lineTo(width, yR * height)
        canvas.drawPath(path, paint)
    }
}