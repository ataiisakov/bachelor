package com.example.xml.presentation.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat


class CustomImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val TAG = "CustomImageView"
    private var currentAngle = 0f
    private val animator = ValueAnimator.ofFloat(0f, -720f).apply {
        duration = 2500L
        interpolator = LinearInterpolator()
        addUpdateListener {
            currentAngle = it.animatedValue as Float
            invalidate()
        }
    }
    private val imgCanvas = Canvas()
    private lateinit var image: Bitmap
    private var canvasSize: Int = 0
    private val paint = Paint().apply {
        isAntiAlias = true
    }
    private val borderPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 25f
    }
    private val oval = RectF()

    init {
        initBitmap()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        invalidate()
    }

    private fun initBitmap() {
        image = when (drawable) {
            is BitmapDrawable -> {
                (drawable as BitmapDrawable).bitmap
            }
            is VectorDrawableCompat, is VectorDrawable -> {
                val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                bitmap
            }
            else -> throw IllegalArgumentException("unsupported drawable type")
        }
        imgCanvas.apply {
            setBitmap(image)
        }

        paint.apply {
            shader = BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resolveSizeAndState(width, widthMeasureSpec, 0)
        val height = resolveSizeAndState(height, heightMeasureSpec, 0)
        val imgSize = if (width < height) width else height
        setMeasuredDimension(imgSize, imgSize)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow: ")
        cancelAnimator()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow: ")
        startAnimator()

    }

    private fun startAnimator() {
        Log.d(TAG, "startAnimator: animator")
        animator.start()
    }

    private fun cancelAnimator() {
        Log.d("CustomImageView", "cancel: animator")
        animator.cancel()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasSize = w
        if (height < canvasSize) {
            canvasSize = h
        }
        borderPaint.apply {
            shader = LinearGradient(
                0f, 0f,
                canvasSize + strokeWidth, canvasSize + strokeWidth,
                Color.BLUE, Color.RED,
                Shader.TileMode.MIRROR
            )
        }
        oval.apply {
            left = paddingLeft * .6f
            top = paddingLeft * .6f
            bottom = canvasSize.toFloat() - paddingLeft * .6f
            right = canvasSize.toFloat() - paddingLeft * .6f
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val circleCenter = canvasSize / 2f
        val borderWidth = borderPaint.strokeWidth

        imgCanvas.drawCircle(circleCenter, circleCenter, circleCenter - borderWidth, paint)
        canvas.rotate(currentAngle, circleCenter, circleCenter)
        canvas.drawOval(oval, borderPaint)
    }
}