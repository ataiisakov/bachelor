package com.example.bachelor.presentation.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.Space
import com.example.bachelor.R
import com.example.bachelor.utils.toPx

class ProfileBackgroundView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val bgBoundsRectF = RectF()
    private val path = Path()
    private val curveBgPath = Path()
    private val photoOval = RectF()
    private val BG_FACTOR = 0.5
    private val handlePoint = PointF()


    lateinit var space: Space
    lateinit var userPhoto: ImageView

    fun setViews(space: Space, userPhotoImageView: ImageView) {
        this.space = space
        this.userPhoto = userPhotoImageView
        requestLayout()
    }

    private val paintPhotoBorder = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 10.toPx().toFloat()
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
        this.space
        this.userPhoto
        drawPathBackground(canvas)
        drawCurvedPathBackground(canvas)
        drawPhotoBorder(canvas)
    }

    private fun drawPhotoBorder(canvas: Canvas) {
        val photoRadius = userPhoto.width / 2f
        val cx = userPhoto.x + photoRadius
        val cy = userPhoto.y + photoRadius
        canvas.drawCircle(cx, cy, photoRadius, paintPhotoBorder)
    }

    private fun drawPathBackground(canvas: Canvas) {


        val photoRadius = userPhoto.width / 2f
        val centerPhotoX = userPhoto.x + photoRadius
        val photoBottom = userPhoto.y + (userPhoto.height).toFloat()

        val photoLeft = userPhoto.x
        val photoTop = userPhoto.y
        val photoRight = centerPhotoX + photoRadius

        photoOval.apply {
            left = photoLeft
            top = photoTop
            right = photoRight
            bottom = photoBottom
        }

        val userBgHeight = (userPhoto.height * BG_FACTOR).toFloat() + photoTop
        bgBoundsRectF.apply {
            left = this@ProfileBackgroundView.left.toFloat()
            top = this@ProfileBackgroundView.top.toFloat()
            right = this@ProfileBackgroundView.right.toFloat()
            bottom = userBgHeight
        }

        path.apply {
            reset()
            moveTo(bgBoundsRectF.left, bgBoundsRectF.top)
            lineTo(bgBoundsRectF.left, bgBoundsRectF.bottom)
            arcTo(photoOval, -180f, 180f, false)
            lineTo(bgBoundsRectF.right, bgBoundsRectF.bottom)
            lineTo(bgBoundsRectF.right, bgBoundsRectF.top)
            close()
        }
        canvas.drawPath(path, backgroundPaint)
    }

    private fun drawCurvedPathBackground(canvas: Canvas) {

        val topCurvedBg = bgBoundsRectF.top + space.y + space.height.toFloat() / 3

        handlePoint.apply {
            x = bgBoundsRectF.left + (bgBoundsRectF.width() * 0.25f)
            y = topCurvedBg
        }

        curveBgPath.apply {
            reset()
            moveTo(bgBoundsRectF.left, bgBoundsRectF.bottom)
            arcTo(photoOval, -180f, 180f, false)
            lineTo(bgBoundsRectF.right, bgBoundsRectF.bottom)
            lineTo(bgBoundsRectF.right, topCurvedBg)
            quadTo(
                handlePoint.x, handlePoint.y,
                bgBoundsRectF.left, bgBoundsRectF.bottom
            )
            close()
        }
        curvedBgPaint.shader = createRadialGradient()
        canvas.drawPath(curveBgPath, curvedBgPaint)
    }

    private fun createRadialGradient(): RadialGradient {

        val colors = intArrayOf(
            resources.getColor(R.color.light_blue_bg_profile),
            resources.getColor(R.color.dark_blue_bg_profile)
        )

        val stops = floatArrayOf(
            0.3f,
            0.8f
        )

        val centerX = bgBoundsRectF.width() / 2f
        val centerY = bgBoundsRectF.bottom

        val radius = bgBoundsRectF.width() / 2f

        return RadialGradient(
            centerX, centerY,
            radius,
            colors,
            stops,
            Shader.TileMode.CLAMP
        )
    }
}