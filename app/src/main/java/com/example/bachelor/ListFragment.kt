package com.example.bachelor

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.PathInterpolator
import androidx.fragment.app.Fragment
import com.example.bachelor.databinding.ListFragmentBinding
import kotlinx.coroutines.NonCancellable.start

class ListFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: ListFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}