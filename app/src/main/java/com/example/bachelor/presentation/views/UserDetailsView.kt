package com.example.bachelor.presentation.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.R
import com.example.bachelor.databinding.MotionCoordinationHeaderBinding
import com.example.bachelor.model.User


class UserDetailsView : MotionLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)


    private var _binding: MotionCoordinationHeaderBinding? = null
    private val binding get() = _binding!!


    var user: User? = null
        set(value) {
            value ?: return
            field = value
//          transition names needed for animation
            with(binding) {
                userPhotoImageView.transitionName =
                    String.format(resources.getString(R.string.shared_image_transition), value.id)
                userNameTextView.transitionName =
                    String.format(resources.getString(R.string.shared_name_transition), value.id)
//          set user properties to views
                userNameTextView.text = value.name
                userPhotoImageView.load(value.photoUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_round_account_circle_56)
                }
            }
        }

//    private var bgBoundsRectF = RectF()
//    private val path = Path()
//    private val curveBgPath = Path()
//    private val photoOval = RectF()
//    private val BG_FACTOR = 0.5
//    private val handlePoint = PointF()
//    private val bgCanvas: Canvas = Canvas()
//    private var bgBitmap: Bitmap? = null
//
//    init {
//        val inflater = LayoutInflater.from(context)
//        _binding = MotionCoordinationHeaderBinding.inflate(inflater,this)
//        val bg = binding.background
//        bgBitmap = Bitmap.createBitmap(bg.width,bg.height, Bitmap.Config.ARGB_8888)
//        bgCanvas.setBitmap(bgBitmap)
//    }


//
//    private val paintPhotoBorder = Paint().apply {
//        isAntiAlias = true
//        style = Paint.Style.STROKE
//        color = Color.WHITE
//        strokeWidth = 10.toPx().toFloat()
//    }
//
//    private val backgroundPaint = Paint().apply {
//        isAntiAlias = true
//        style = Paint.Style.FILL
//        color = resources.getColor(R.color.light_blue_bg_profile)
//    }
//
//    private val curvedBgPaint = Paint().apply {
//        isAntiAlias = true
//        style = Paint.Style.FILL
//        color = resources.getColor(R.color.dark_blue_bg_profile)
//    }
//
//    override fun dispatchDraw(canvas: Canvas) {
//        drawPathBackground(canvas)
//        drawCurvedPathBackground(canvas)
//        drawPhotoBorder(canvas)
//        super.dispatchDraw(canvas)
//    }
//
//    private fun drawPhotoBorder(canvas: Canvas) {
//        val userPhoto = binding.userPhotoImageView
//
//        val photoRadius = userPhoto.width / 2f
//        val cx = userPhoto.x + photoRadius
//        val cy = userPhoto.y + photoRadius
//        canvas.drawCircle(cx, cy, photoRadius, paintPhotoBorder)
//    }
//
//    private fun drawPathBackground(canvas: Canvas) {
//        val userPhoto = binding.userPhotoImageView
//
//        val photoRadius = userPhoto.width / 2f
//        val centerPhotoX = userPhoto.x + photoRadius
//        val photoBottom = userPhoto.y + (userPhoto.height).toFloat()
//
//        val photoLeft = userPhoto.x
//        val photoTop = userPhoto.y
//        val photoRight = centerPhotoX + photoRadius
//
//        photoOval.apply {
//            left = photoLeft
//            top = photoTop
//            right = photoRight
//            bottom = photoBottom
//        }
//
//        val userBgHeight = (userPhoto.height * BG_FACTOR).toFloat() + photoTop
//        bgBoundsRectF.apply {
//            left = this@UserDetailsView.left.toFloat()
//            top = this@UserDetailsView.top.toFloat()
//            right = this@UserDetailsView.right.toFloat()
//            bottom = userBgHeight
//        }
//
//        path.apply {
//            reset()
//            moveTo(bgBoundsRectF.left, bgBoundsRectF.top)
//            lineTo(bgBoundsRectF.left, bgBoundsRectF.bottom)
//            arcTo(photoOval, -180f, 180f, false)
//            lineTo(bgBoundsRectF.right, bgBoundsRectF.bottom)
//            lineTo(bgBoundsRectF.right, bgBoundsRectF.top)
//            close()
//        }
//        canvas.drawPath(path, backgroundPaint)
//    }
//
//    private fun drawCurvedPathBackground(canvas: Canvas) {
//        val space = binding.space
//
//        val topCurvedBg = bgBoundsRectF.top + space.y + space.height.toFloat() / 3
//
//        handlePoint.apply {
//            x = bgBoundsRectF.left + (bgBoundsRectF.width() * 0.25f)
//            y = topCurvedBg
//        }
//
//        curveBgPath.apply {
//            reset()
//            moveTo(bgBoundsRectF.left, bgBoundsRectF.bottom)
//            arcTo(photoOval, -180f, 180f, false)
//            lineTo(bgBoundsRectF.right, bgBoundsRectF.bottom)
//            lineTo(bgBoundsRectF.right, topCurvedBg)
//            quadTo(
//                handlePoint.x, handlePoint.y,
//                bgBoundsRectF.left, bgBoundsRectF.bottom
//            )
//            close()
//        }
//        curvedBgPaint.shader = createRadialGradient()
//        canvas.drawPath(curveBgPath, curvedBgPaint)
//    }
//
//    private fun createRadialGradient(): RadialGradient {
//
//        val colors = intArrayOf(
//            resources.getColor(R.color.light_blue_bg_profile),
//            resources.getColor(R.color.dark_blue_bg_profile)
//        )
//
//        val stops = floatArrayOf(
//            0.3f,
//            0.8f
//        )
//
//        val centerX = bgBoundsRectF.width() / 2f
//        val centerY = bgBoundsRectF.bottom
//
//        val radius = bgBoundsRectF.width() / 2f
//
//        return RadialGradient(
//            centerX, centerY,
//            radius,
//            colors,
//            stops,
//            Shader.TileMode.CLAMP
//        )
//    }

}