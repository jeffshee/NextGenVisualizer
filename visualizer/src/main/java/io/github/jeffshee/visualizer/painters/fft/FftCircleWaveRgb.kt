package io.github.jeffshee.visualizer.painters.fft

import android.graphics.*
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class FftCircleWaveRgb(
    flags: Int = Paint.ANTI_ALIAS_FLAG,
    var color: List<Int> = listOf(Color.RED, Color.GREEN, Color.BLUE),
    //
    startHz: Int = 0,
    endHz: Int = 2000,
    //
    num: Int = 128,
    interpolator: String = "sp",
    //
    side: String = "a",
    mirror: Boolean = false,
    power: Boolean = true,
    //
    radiusR: Float = .4f,
    ampR: Float = .6f,
    var rot: Float = 10f

) : Painter() {

    private val wave = FftCircleWave(Paint(flags).apply {
        style = Paint.Style.FILL;xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
    }, startHz, endHz, num, interpolator, side, mirror, power, radiusR, ampR)

    override fun calc(helper: VisualizerHelper) {
        wave.calc(helper)
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        wave.paint.color = color[0]
        wave.draw(canvas, helper)
        rotateHelper(canvas, rot, .5f, .5f) {
            wave.paint.color = color[1]
            wave.draw(canvas, helper)
        }
        rotateHelper(canvas, rot * 2, .5f, .5f) {
            wave.paint.color = color[2]
            wave.draw(canvas, helper)
        }
    }
}