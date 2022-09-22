package com.example.bachelor.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        with(binding) {
            userPhotoImageView.transitionName =
                String.format(resources.getString(R.string.shared_image_transition), user.id)
            userNameTextView.transitionName =
                String.format(resources.getString(R.string.shared_name_transition), user.id)
            userPhotoImageView.load(user.photoUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_round_account_circle_56)
            }
            userNameTextView.text = user.name
        }
        view.doOnPreDraw {
            startPostponedEnterTransition()
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