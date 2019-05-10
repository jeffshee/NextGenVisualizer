package io.github.jeffshee.visualizer.painters.waveform

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class Waveform(
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE;style = Paint.Style.STROKE;strokeWidth = 2f
    },
    var startHz: Int = 0,
    var endHz: Int = 2000,
    var sliceNum: Int = 256,
    var side: String = "a",
    var xR: Float = 0f,
    var yR: Float = .5f,
    var wR: Float = 1f,
    var ampR: Float = 1f
) : Painter() {

    private val path = Path()

    @ExperimentalUnsignedTypes
    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(startHz, endHz)
        if (isQuiet(fft)) return

        val width = canvas.width.toFloat() * wR

        val wave = helper.getWave()
        val point = wave.size / (sliceNum + 1)
        val sliceWidth = width / sliceNum

        path.moveTo(0f, (-wave[0].toUByte().toInt() + 128f) * ampR)
        for (i in 1..sliceNum)
            path.lineTo(sliceWidth * i, (-wave[point * i].toUByte().toInt() + 128f) * ampR)
        drawHelper(canvas, side, xR, yR) { canvas.drawPath(path, paint) }
        path.reset()
    }
}