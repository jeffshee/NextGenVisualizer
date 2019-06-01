package io.github.jeffshee.visualizer.painters.fft

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.utils.VisualizerHelper
import kotlin.math.PI

class FftCircleWave(
    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    },
    var startHz: Int = 0,
    var endHz: Int = 2000,
    var sliceNum: Int = 128,
    var interpolator: String = "sp",
    var side: String = "a",
    var xR: Float = .5f,
    var yR: Float = .5f,
    var baseR: Float = .4f,
    var ampR: Float = .6f
    ) : Painter() {

    private val path = Path()
    private var points = Array(0) { GravityModel() }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        val fft = helper.getFftMagnitudeRange(startHz, endHz)

        val circleFft = this.getCircleFft(fft)
        if (points.size != circleFft.size) points = Array(circleFft.size) { GravityModel(0f) }
        points.forEachIndexed { index, bar -> bar.update(circleFft[index].toFloat() * ampR) }
        val psf = interpolateFftCircle(points, sliceNum, interpolator)

        val angle = 2 * PI.toFloat() / sliceNum

        drawHelper(canvas, side, xR, yR, {
            for (i in 0..sliceNum) {
                val point = toCartesian(canvas.width / 2f * baseR + psf.value(i.toDouble()).toFloat(), angle * i)
                if (i == 0) path.moveTo(point[0], point[1])
                else path.lineTo(point[0], point[1])
            }
            path.close()
            canvas.drawPath(path, paint)
            path.reset()
        }, {
            for (i in 0..sliceNum) {
                val point = toCartesian(canvas.width / 2f * baseR, angle * i)
                if (i == 0) path.moveTo(point[0], point[1])
                else path.lineTo(point[0], point[1])
            }
            path.close()
            for (i in 0..sliceNum) {
                val point = toCartesian(canvas.width / 2f * baseR - psf.value(i.toDouble()).toFloat(), angle * i)
                if (i == 0) path.moveTo(point[0], point[1])
                else path.lineTo(point[0], point[1])
            }
            path.close()
            path.fillType = Path.FillType.EVEN_ODD
            canvas.drawPath(path, paint)
            path.reset()
        }, {
            for (i in 0..sliceNum) {
                val point = toCartesian(canvas.width / 2f * baseR + psf.value(i.toDouble()).toFloat(), angle * i)
                if (i == 0) path.moveTo(point[0], point[1])
                else path.lineTo(point[0], point[1])
            }
            path.close()
            for (i in 0..sliceNum) {
                val point = toCartesian(canvas.width / 2f * baseR - psf.value(i.toDouble()).toFloat(), angle * i)
                if (i == 0) path.moveTo(point[0], point[1])
                else path.lineTo(point[0], point[1])
            }
            path.close()
            path.fillType = Path.FillType.EVEN_ODD
            canvas.drawPath(path, paint)
            path.reset()
        })
    }


}