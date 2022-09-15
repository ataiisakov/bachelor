package com.example.bachelor.presentation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply{
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.RED
    }
    private val path = Path().apply {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = 100f
        val oval = RectF(centerX,centerY,centerX+radius,centerY+radius)
        arcTo(oval,0f,360f,false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.clipPath(path)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = measuredHeight.coerceAtMost(measuredWidth)
        setMeasuredDimension(size,size)
    }


}