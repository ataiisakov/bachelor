package com.example.bachelor.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.example.bachelor.R
import com.example.bachelor.databinding.MotionCoordinationBinding
import com.example.bachelor.model.User
import com.example.bachelor.presentation.views.UserDetailsView

class DetailFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: MotionCoordinationBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MotionCoordinationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val user = requireArguments().get(ARG_USER) as User
        val userDetailsView =
            binding.root.findViewById<MotionLayout>(R.id.motionLayout) as UserDetailsView
        userDetailsView.user = user
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