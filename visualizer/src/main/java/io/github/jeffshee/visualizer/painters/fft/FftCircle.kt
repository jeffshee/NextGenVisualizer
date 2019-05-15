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
    var beatStartHz: Int = 60,
    var beatEndHz: Int = 800,
    var barNum: Int = 64,
    var interpolator: String = "li",
    var side: String = "a",
    var xR: Float = .5f,
    var yR: Float = .5f,
    var baseR: Float = .6f,
    var ampR: Float = .3f,
    var peak: Float = 200f,
    var rpm: Float = 1f
) : Painter() {

    private var bars = Array(barNum) { GravityModel() }
    private var rot: Float = 0f

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(startHz, endHz)
        if (isQuiet(fft)) return

        val fftBeat = helper.getFftMagnitudeRange(beatStartHz, beatEndHz)
        val psf = interpolateFftBar(getCircleFft(fft), barNum, interpolator)

        val angle = 2 * PI.toFloat() / barNum
        val pts = FloatArray(4 * barNum)

        rotateHelper(canvas, rot, xR, yR) {
            drawHelper(canvas, side, xR, yR, {
                bars.forEachIndexed { index, bar ->
                    bar.update(psf.value(index.toDouble()).toFloat())
                    val start =
                        toCartesian(
                            canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR),
                            angle * index
                        )
                    val stop = toCartesian(
                        canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR) + bar.height,
                        angle * index
                    )
                    pts[4 * index] = start[0];pts[4 * index + 1] = start[1]
                    pts[4 * index + 2] = stop[0];pts[4 * index + 3] = stop[1]
                }
                canvas.drawLines(pts, paint)
            }, {
                bars.forEachIndexed { index, bar ->
                    bar.update(psf.value(index.toDouble()).toFloat())
                    val start =
                        toCartesian(
                            canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR),
                            angle * index
                        )
                    val stop = toCartesian(
                        canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR) - bar.height,
                        angle * index
                    )
                    pts[4 * index] = start[0];pts[4 * index + 1] = start[1]
                    pts[4 * index + 2] = stop[0];pts[4 * index + 3] = stop[1]
                }
                canvas.drawLines(pts, paint)
            }, {
                bars.forEachIndexed { index, bar ->
                    bar.update(psf.value(index.toDouble()).toFloat())
                    val start =
                        toCartesian(
                            canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR) + bar.height,
                            angle * index
                        )
                    val stop = toCartesian(
                        canvas.width / 2f * (baseR + getEnergy(fftBeat).toFloat() / peak * ampR) - bar.height,
                        angle * index
                    )
                    pts[4 * index] = start[0];pts[4 * index + 1] = start[1]
                    pts[4 * index + 2] = stop[0];pts[4 * index + 3] = stop[1]
                }
                canvas.drawLines(pts, paint)
            })
        }

        if (rpm != 0f) {
            rot += rpm / 10f
            rot %= 360f
        }
    }


}