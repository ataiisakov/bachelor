package com.example.bachelor.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.metrics.performance.PerformanceMetricsState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bachelor.adapter.UsersAdapter
import com.example.bachelor.adapter.FooterAdapter
import com.example.bachelor.adapter.HeaderAdapter
import com.example.bachelor.databinding.ListFragmentBinding
import com.example.bachelor.model.User
import com.example.bachelor.model.UserRepository
import com.example.bachelor.adapter.onListItemClickListener

class ListFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: ListFragmentBinding? = null

    private var metricsStateHolder: PerformanceMetricsState.Holder? = null
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            // check if JankStats is initialized and skip adding state if not
            val metricsState = metricsStateHolder?.state ?: return

            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    metricsState.putState("RecyclerView", "Dragging")
                }
                RecyclerView.SCROLL_STATE_SETTLING -> {
                    metricsState.putState("RecyclerView", "Settling")
                }
                else -> {
                    metricsState.removeState("RecyclerView")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        metricsStateHolder = PerformanceMetricsState.getHolderForHierarchy(view)
        val headerAdapter = HeaderAdapter()
        val footerAdapter = FooterAdapter()
        val usersAdapter = UsersAdapter(listener = object : onListItemClickListener {
            override fun invoke(user: User, view: View?) {
                navigator().showDetails(user, view)
            }
        })
        val concatAdapter = ConcatAdapter(headerAdapter, usersAdapter,footerAdapter)
        with(binding) {
            recycleView.adapter = concatAdapter
            recycleView.addOnScrollListener(scrollListener)
        }
        usersAdapter.submitList(UserRepository.users)
        postponeEnterTransition()
        binding.recycleView.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onDestroyView() {
        _binding = null
        metricsStateHolder = null
        super.onDestroyView()
    }
}