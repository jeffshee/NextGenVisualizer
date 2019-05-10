package io.github.jeffshee.visualizer.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.painters.SimpleText
import io.github.jeffshee.visualizer.utils.FrameManager
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class VisualizerView : View {

    private val frameManager = FrameManager()
    private lateinit var painterList: List<Painter>
    private lateinit var helper: VisualizerHelper
    private lateinit var simpleText: SimpleText
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var showFps = true

    constructor(context: Context) : super(context) {
        onCreateView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        onCreateView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        onCreateView()
    }

    fun setPainterList(visualizerHelper: VisualizerHelper, list: List<Painter>) {
        helper = visualizerHelper
        painterList = list
    }

    private fun onCreateView() {
        simpleText = SimpleText(Paint().apply { color = Color.WHITE;textSize = dp2px(12f) })
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                invalidate()
                handler.postDelayed(this, 15)
            }
        }
        handler.post(runnable)
    }

    override fun onDraw(canvas: Canvas?) {
        setLayerType(LAYER_TYPE_HARDWARE, paint)
        if (showFps) {
            canvas?.apply {
                painterList.forEach { it.draw(canvas, helper) }
                simpleText.text = "FPS: ${frameManager.fps()}"
                simpleText.draw(canvas, helper)
            }
        }
        frameManager.tick()
        super.onDraw(canvas)
    }

    private fun dp2px(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }
}