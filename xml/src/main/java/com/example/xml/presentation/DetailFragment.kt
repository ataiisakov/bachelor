package com.example.xml.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.metrics.performance.PerformanceMetricsState
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.model.User
import com.example.xml.R
import com.example.xml.databinding.DetailFragmentBinding

class DetailFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: DetailFragmentBinding? = null

    lateinit var userPhotoImageView: ImageView
    lateinit var userNameTextView: TextView

    private var metricsStateHolder: PerformanceMetricsState.Holder? = null

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
        metricsStateHolder = PerformanceMetricsState.getHolderForHierarchy(view)
        metricsStateHolder?.state?.putState("Fragment", javaClass.simpleName)
        val user = requireArguments().get(ARG_USER) as User
        setUserDataViews(user)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setUserDataViews(user: User) {
        userPhotoImageView = binding.root.findViewById(R.id.iv)
        userNameTextView   = binding.root.findViewById(R.id.tv)
        //          set user properties to views
        userNameTextView.text = user.name
        userPhotoImageView.load(user.photoUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_round_account_circle_56)
        }
    }


    override fun onDestroyView() {
        _binding = null
        metricsStateHolder = null
        super.onDestroyView()
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