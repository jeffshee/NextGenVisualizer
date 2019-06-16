package io.github.jeffshee.visualizer.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import io.github.jeffshee.visualizer.painters.Painter
import io.github.jeffshee.visualizer.painters.misc.SimpleText
import io.github.jeffshee.visualizer.utils.FrameManager
import io.github.jeffshee.visualizer.utils.VisualizerHelper

class VisualizerView : View {

    private val frameManager = FrameManager()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var painterList: List<Painter>
    private lateinit var helper: VisualizerHelper
    private lateinit var simpleText: SimpleText

    var anim = true
    var fps = true

    companion object {
        private fun dp2px(resources: Resources, dp: Float): Float {
            return dp * resources.displayMetrics.density
        }
    }

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
        simpleText = SimpleText(Paint().apply {
            color = Color.WHITE;textSize = dp2px(resources, 12f)
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(this::painterList.isInitialized && this::helper.isInitialized){
            setLayerType(LAYER_TYPE_HARDWARE, paint)
            canvas?.apply {
                painterList.forEach {
                    it.calc(helper)
                    it.draw(canvas, helper) }
                simpleText.text = "FPS: ${frameManager.fps()}"
                if (fps) simpleText.draw(canvas, helper)
            }
            frameManager.tick()
            if (anim) invalidate()
        }
    }
}