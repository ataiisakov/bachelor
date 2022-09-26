package com.example.bachelor.presentation

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.R
import com.example.bachelor.model.User
import com.example.bachelor.utils.toPx


class UserDetailsView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    private lateinit var userPhoto: ImageView
    private lateinit var userName: TextView
    private lateinit var longText: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.user_details_view, this, true)
        userPhoto = findViewById(R.id.userPhotoImageView)
        userName = findViewById(R.id.userNameTextView)
        longText = findViewById(R.id.long_text)
    }

    var user: User? = null
        set(value) {
            value ?: return
            field = value
//          transition names needed for animation
            userName.transitionName =
                String.format(resources.getString(R.string.shared_image_transition), value.id)
            userPhoto.transitionName =
                String.format(resources.getString(R.string.shared_name_transition), value.id)
//          set user properties
            userName.text = value.name
            userPhoto.load(value.photoUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_round_account_circle_56)
            }
            longText.text = context.resources.getText(R.string.lorem_text)
        }

    private val profileRect = RectF()
    private val path = Path()
    private val FACTOR = 0.8

    private val paintPhotoBorder = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 10.toPx().toFloat()
    }

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.BLUE
    }


    override fun dispatchDraw(canvas: Canvas) {
        drawPathBackground(canvas)
//        drawPhotoBorder(canvas)
        super.dispatchDraw(canvas)
    }


    private fun drawBackground(canvas: Canvas) {
        val bgRight = width
        val bgBottom = userPhoto.height * FACTOR
        profileRect.apply {
            top = 0f
            left = 0f
            right = bgRight.toFloat()
            bottom = bgBottom.toFloat()
        }
        canvas.drawRect(profileRect, backgroundPaint)
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
        val centerPhotoY = userPhoto.y + photoRadius
        val photoBottom = (userPhoto.height * FACTOR).toFloat()

        val photoLeft = userPhoto.x
        val photoTop = userPhoto.y
        val photoRight = centerPhotoX + photoRadius

        val oval = RectF(
            photoLeft,
            photoTop,
            photoRight,
            photoBottom
        )
        path.apply {
            moveTo(0f, 0f)
            lineTo(0f, photoBottom)
            lineTo(photoLeft, photoBottom)
            arcTo(oval, -180f, 180f, false)
            lineTo(right.toFloat(), photoBottom)
            lineTo(right.toFloat(), 0f)
            close()
        }
        canvas.drawPath(path, backgroundPaint)
    }

}