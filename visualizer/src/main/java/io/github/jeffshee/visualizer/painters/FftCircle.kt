package io.github.jeffshee.visualizer.painters

import android.graphics.Canvas
import android.graphics.Paint
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlin.math.PI

class FftCircle(private val paint: Paint) : Painter() {
    var startHz = 0
    var endHz = 2000
    var beatStartHz = 60
    var beatEndHz = 800
    var baseR = 0.6f
    var ampR = 0.3f
    var peak = 200f
    var rpm = 1f
    var barNum = 64
    var interpolator = "li"

    private var width: Float = 0f
    private var height: Float = 0f
    private var bars: Array<GravityModel> = Array(barNum) { GravityModel(0f) }
    private var rot: Float = 0f

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(helper, startHz, endHz)
        if (isQuiet(fft)) return
        val fftBeat = helper.getFftMagnitudeRange(helper, beatStartHz, beatEndHz)
        val psf = interpolateFftBar(patchCircle(fft), barNum, interpolator)
        width = canvas.width.toFloat()
        height = canvas.height.toFloat()

        val angle = 2 * PI.toFloat() / barNum
        canvas.save()
        canvas.translate(canvas.width / 2f, canvas.height / 2f)
        bars.forEachIndexed { index, bar ->
            bar.update(psf.value(index.toDouble()).toFloat())
            val start =
                toCartesian(
                    canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR),
                    angle * index + rot
                )
            val stop = toCartesian(
                canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR) + bar.height,
                angle * index + rot
            )
            canvas.drawLine(start[0], start[1], stop[0], stop[1], paint)
        }
        if (rpm != 0f) {
            // rpm -> rad/frame, let fps = 60
            rot += rpm * 2 * PI.toFloat() / 3600
            rot %= (2 * PI.toFloat())
        }
        canvas.restore()
    }


}