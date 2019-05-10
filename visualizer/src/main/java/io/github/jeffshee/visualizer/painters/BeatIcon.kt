package io.github.jeffshee.visualizer.painters

import android.graphics.*
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class BeatIcon(private val paint: Paint) : Painter() {
    var startHz = 60
    var endHz = 800
    var baseR = 0.3f
    var ampR = 0.3f
    var peak = 200f
    var bitmap: Bitmap? = null
    private val matrix = Matrix()
    private val circle = GravityModel(0f)

    private var width: Float = 0f
    private var height: Float = 0f

    companion object {
        fun getCircledBitmap(bitmap: Bitmap): Bitmap {
            val tmpBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val tmpCanvas = Canvas(tmpBitmap)
            val tmpPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            val tmpRect = Rect(0, 0, bitmap.width, bitmap.height)
            tmpCanvas.drawARGB(0, 0, 0, 0)
            tmpCanvas.drawCircle(bitmap.width / 2f, bitmap.height / 2f, bitmap.width / 2f, tmpPaint)
            tmpPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            tmpCanvas.drawBitmap(bitmap, tmpRect, tmpRect, tmpPaint)
            return tmpBitmap
        }
    }

    override fun draw(canvas: Canvas, helper: VisualizerHelper) {
        width = canvas.width.toFloat()
        height = canvas.height.toFloat()
        bitmap?.apply bitmap@{
            val fft = helper.getFftMagnitudeRange(startHz, endHz)
            circle.update(canvas.width * (baseR + getEnergy(fft).toFloat() / peak * ampR))
            val radius = circle.height
            matrix.apply {
                postScale(radius / this@bitmap.width, radius / this@bitmap.width)
                postTranslate(-radius / 2f, -radius / 2f)
            }
            canvas.save()
            canvas.translate(canvas.width / 2f, canvas.height / 2f)
            canvas.drawBitmap(this, matrix, null)
            matrix.reset()
            canvas.restore()
        }
    }
}