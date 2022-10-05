package com.example.bachelor.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.R
import com.example.bachelor.databinding.DetailFragmentBinding
import com.example.bachelor.model.User

class DetailFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: DetailFragmentBinding? = null

    lateinit var userPhotoImageView: ImageView
    lateinit var userNameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val user = requireArguments().get(ARG_USER) as User
        setUserDataViews(user)
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setUserDataViews(user: User) {
        userPhotoImageView = binding.root.findViewById<ImageView>(R.id.iv)
        userNameTextView = binding.root.findViewById<TextView>(R.id.tv)
        userPhotoImageView.transitionName =
            String.format(resources.getString(R.string.shared_image_transition), user.id)
        userNameTextView.transitionName =
            String.format(resources.getString(R.string.shared_name_transition), user.id)
        //          set user properties to views
        userNameTextView.text = user.name
        userPhotoImageView.load(user.photoUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_round_account_circle_56)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_USER = "ARG_USER"
        fun newInstance(user: User): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundleOf(ARG_USER to user)
            return fragment
        }
    }
}