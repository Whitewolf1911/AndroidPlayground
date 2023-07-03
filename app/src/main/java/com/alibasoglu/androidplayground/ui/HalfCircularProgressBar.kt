package com.alibasoglu.androidplayground.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.alibasoglu.androidplayground.R

class HalfCircularProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val progressStrokeWidth: Float = 20f
    private val backgroundStrokeWidth: Float = 10f

    private var progressBarPaint: Paint = Paint()
    private var backgroundPaint: Paint = Paint()
    private var rectF: RectF = RectF()
    private var progress: Float = 0f

    init {
        progressBarPaint.color = ContextCompat.getColor(context, R.color.purple_200)
        progressBarPaint.style = Paint.Style.STROKE
        progressBarPaint.strokeWidth = progressStrokeWidth
        progressBarPaint.strokeCap = Paint.Cap.ROUND // gives the corner radius

        backgroundPaint.color = ContextCompat.getColor(context, R.color.teal_200)
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = backgroundStrokeWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(rectF, 180f, 180f, false, backgroundPaint)
        canvas.drawArc(rectF, 180f, progress * 1.8f, false, progressBarPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.set(
            progressStrokeWidth / 2,
            progressStrokeWidth / 2,
            w - progressStrokeWidth / 2,
            h - progressStrokeWidth / 2
        )
    }

    fun setProgress(progress: Int) {
        this.progress = progress.toFloat()
        invalidate() // notify view to redraw itself
    }
}

