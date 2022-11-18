package com.example.xml.presentation.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.xml.R
import com.example.xml.utils.toDp

class AvatarBackgroundView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val bgBoundsRectF = RectF()
    private val path = Path()
    private val curveBgPath = Path()
    private val handlePoint = PointF()


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        bgBoundsRectF.apply {
            this.left = this@AvatarBackgroundView.left.toFloat()
            this.right = this@AvatarBackgroundView.right.toFloat()
            this.top = this@AvatarBackgroundView.top.toFloat()
            this.bottom = this@AvatarBackgroundView.bottom.toFloat()
        }
    }

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = resources.getColor(R.color.light_blue_bg_profile)
    }

    private val curvedBgPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = resources.getColor(R.color.dark_blue_bg_profile)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val hDp = height.toDp()

        drawPathBackground(canvas)
//        draw only if height is more than 100 dp
        if(hDp > 100){
            drawCurvedPathBackground(canvas)
        }
    }

    private fun drawPathBackground(canvas: Canvas) {

        path.apply {
            reset()
            moveTo(bgBoundsRectF.left, bgBoundsRectF.top)
            lineTo(bgBoundsRectF.left, bgBoundsRectF.bottom)
            lineTo(bgBoundsRectF.right, bgBoundsRectF.bottom)
            lineTo(bgBoundsRectF.right, bgBoundsRectF.top)
            close()
        }
        canvas.drawPath(path, backgroundPaint)
    }

    private fun drawCurvedPathBackground(canvas: Canvas) {

        val topCurvedBg = bgBoundsRectF.height() * 0.2f

        handlePoint.apply {
            x = bgBoundsRectF.left + (bgBoundsRectF.width() * 0.25f)
            y = topCurvedBg
        }

        curveBgPath.apply {
            reset()
            moveTo(bgBoundsRectF.left, bgBoundsRectF.bottom)
            lineTo(bgBoundsRectF.right, bgBoundsRectF.bottom)
            lineTo(bgBoundsRectF.right, topCurvedBg)
            quadTo(
                handlePoint.x, handlePoint.y,
                bgBoundsRectF.left, bgBoundsRectF.bottom
            )
            close()
        }
        canvas.drawPath(curveBgPath, curvedBgPaint)
    }
}