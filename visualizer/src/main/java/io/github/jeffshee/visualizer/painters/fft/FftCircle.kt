package io.github.jeffshee.visualizer.painters.fft

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlin.math.PI

class FftCircle(
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE;style = Paint.Style.STROKE;strokeWidth = 2f
    },
    var startHz: Int = 0,
    var endHz: Int = 2000,
    var barNum: Int = 64,
    var interpolator: String = "li",
    var side: String = "a",
    var xR: Float = .5f,
    var yR: Float = .5f,
    var baseR: Float = .4f,
    var ampR: Float = 1f,
    var enableBoost: Boolean = true
) : Painter() {

    private var points = Array(0) { GravityModel() }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(startHz, endHz)
        if (isQuiet(fft)) return

        var circleFft = this.getCircleFft(fft)
        if(enableBoost) circleFft = boost(circleFft)

        if (points.size != circleFft.size) points = Array(circleFft.size) { GravityModel(0f) }
        points.forEachIndexed { index, bar -> bar.update(circleFft[index].toFloat() * ampR) }
        val psf = interpolateFftCircle(points, barNum, interpolator)

        val angle = 2 * PI.toFloat() / barNum
        val pts = FloatArray(4 * barNum)

        drawHelper(canvas, side, xR, yR, {
            for (i in 0 until barNum) {
                val start =
                    toCartesian(canvas.width / 2f * baseR, angle * i)
                val stop = toCartesian(
                    canvas.width / 2f * baseR + psf.value(i.toDouble()).toFloat(), angle * i
                )
                pts[4 * i] = start[0];pts[4 * i + 1] = start[1]
                pts[4 * i + 2] = stop[0];pts[4 * i + 3] = stop[1]
            }
            canvas.drawLines(pts, paint)
        }, {
            for (i in 0 until barNum) {
                val start =
                    toCartesian(canvas.width / 2f * baseR, angle * i)
                val stop = toCartesian(
                    canvas.width / 2f * baseR - psf.value(i.toDouble()).toFloat(), angle * i
                )
                pts[4 * i] = start[0];pts[4 * i + 1] = start[1]
                pts[4 * i + 2] = stop[0];pts[4 * i + 3] = stop[1]
            }
            canvas.drawLines(pts, paint)
        }, {
            for (i in 0 until barNum) {
                val start =
                    toCartesian(
                        canvas.width / 2f * baseR + psf.value(i.toDouble()).toFloat(), angle * i
                    )
                val stop = toCartesian(
                    canvas.width / 2f * baseR - psf.value(i.toDouble()).toFloat(), angle * i
                )
                pts[4 * i] = start[0];pts[4 * i + 1] = start[1]
                pts[4 * i + 2] = stop[0];pts[4 * i + 3] = stop[1]
            }
            canvas.drawLines(pts, paint)
        })
    }
}