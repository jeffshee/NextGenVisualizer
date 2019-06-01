package io.github.jeffshee.visualizer.painters.fft

import android.graphics.*
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftCircleWaveRgb(
    flags: Int = Paint.ANTI_ALIAS_FLAG,
    startHzR: Int = 0,
    endHzR: Int = 2000,
    startHzG: Int = 0,
    endHzG: Int = 2000,
    startHzB: Int = 0,
    endHzB: Int = 2000,
    sliceNum: Int = 128,
    interpolator: String = "sp",
    side: String = "a",
    var xR: Float = .5f,
    var yR: Float = .5f,
    var color: List<Int> = listOf(Color.RED, Color.GREEN, Color.BLUE)

) : Painter() {

    private val waveR = FftCircleWave(Paint(flags).apply {
        style = Paint.Style.FILL;color = this@FftCircleWaveRgb.color[0];xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }, startHzR, endHzR, sliceNum, interpolator, side, xR, yR)

    private val waveG = FftCircleWave(Paint(flags).apply {
        style = Paint.Style.FILL;color = this@FftCircleWaveRgb.color[1];xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }, startHzG, endHzG, sliceNum, interpolator, side, xR, yR)

    private val waveB = FftCircleWave(Paint(flags).apply {
        style = Paint.Style.FILL;color = this@FftCircleWaveRgb.color[2];xfermode = PorterDuffXfermode(
        PorterDuff.Mode.ADD
    )
    }, startHzB, endHzB, sliceNum, interpolator, side, xR, yR)

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        waveR.draw(canvas, helper)
        rotateHelper(canvas, 5f, xR, yR){waveG.draw(canvas, helper)}
        rotateHelper(canvas, 10f, xR, yR){waveB.draw(canvas, helper)}

    }
}