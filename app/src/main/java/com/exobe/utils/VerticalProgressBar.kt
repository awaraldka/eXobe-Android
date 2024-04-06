package com.exobe.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.exobe.R

class VerticalProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint()
    private val progressPaint = Paint()

    private var progress = 0
    private val maxProgress = 100 // Change this to your desired maximum progress
    private val backgroundColor = ContextCompat.getColor(context, R.color.grey)
    private val progressColor = ContextCompat.getColor(context, R.color.red) // Change to your desired fill color

    init {
        backgroundPaint.color = backgroundColor
        progressPaint.color = progressColor
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate() // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Calculate the top position of the progress based on the progress value
        val progressTop = (progress.toFloat() / maxProgress) * height

        // Draw background
        canvas?.drawRect(0f, 0f, width, height, backgroundPaint)

        // Draw progress
        canvas?.drawRect(0f, 0f, width, progressTop, progressPaint)
    }



}
