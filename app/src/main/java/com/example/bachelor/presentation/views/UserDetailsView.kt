package com.example.bachelor.presentation.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.R
import com.example.bachelor.databinding.MotionCoordinationHeaderBinding
import com.example.bachelor.model.User


class UserDetailsView : ConstraintLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)


    private var _binding: MotionCoordinationHeaderBinding? = null
    private val binding get() = _binding!!

    lateinit var profileBackgroundView: ProfileBackgroundView

    init {
        _binding = MotionCoordinationHeaderBinding.inflate(
            LayoutInflater.from(context),
            this, true
        )
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        profileBackgroundView = binding.background
        profileBackgroundView.setViews(binding.space, binding.userPhotoImageView)
    }

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
}