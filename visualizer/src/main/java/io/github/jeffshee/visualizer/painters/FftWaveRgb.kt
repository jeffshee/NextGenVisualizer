package io.github.jeffshee.visualizer.painters

import android.graphics.*
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftWaveRgb(
    flags: Int = Paint.ANTI_ALIAS_FLAG,
    startHzR: Int = 0,
    endHzR: Int = 1000,
    startHzG: Int = 400,
    endHzG: Int = 1500,
    startHzB: Int = 800,
    endHzB: Int = 2000,
    sliceNum: Int = 128,
    interpolator: String = "sp",
    side: String = "a",
    mode: String = "",
    xR: Float = 0f,
    yR: Float = 1f,
    wR: Float = 1f,
    ampR: Float = 1f,
    var color: List<Int> = listOf(Color.RED, Color.GREEN, Color.BLUE)

) : Painter() {

    private val waveR = FftWave(Paint(flags).apply {
        style = Paint.Style.FILL;color = this@FftWaveRgb.color[0];xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }, startHzR, endHzR, sliceNum, interpolator, side, mode, xR, yR, wR, ampR)

    private val waveG = FftWave(Paint(flags).apply {
        style = Paint.Style.FILL;color = this@FftWaveRgb.color[1];xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }, startHzG, endHzG, sliceNum, interpolator, side, mode, xR, yR, wR, ampR)

    private val waveB = FftWave(Paint(flags).apply {
        style = Paint.Style.FILL;color = this@FftWaveRgb.color[2];xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }, startHzB, endHzB, sliceNum, interpolator, side, mode, xR, yR, wR, ampR)

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        waveR.draw(canvas, helper)
        waveG.draw(canvas, helper)
        waveB.draw(canvas, helper)
    }
}