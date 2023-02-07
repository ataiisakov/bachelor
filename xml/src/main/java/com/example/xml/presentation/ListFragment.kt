package com.example.xml.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.metrics.performance.PerformanceMetricsState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bachelor.model.User
import com.example.bachelor.model.UserRepository
import com.example.xml.adapter.FooterAdapter
import com.example.xml.adapter.HeaderAdapter
import com.example.xml.adapter.UsersAdapter
import com.example.xml.adapter.onListItemClickListener
import com.example.xml.databinding.ListFragmentBinding

class ListFragment : Fragment() {
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
        setupAdapters()
        postponeEnterTransition()
        binding.recycleView.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setupAdapters() {
        val headerAdapter = HeaderAdapter()
        val footerAdapter = FooterAdapter()
        val usersAdapter = UsersAdapter(
            UserRepository.users,
            listener = object : onListItemClickListener {
                override fun invoke(user: User) {
                    navigator().showDetails(user)
                }
            }
        )
        val concatAdapter = ConcatAdapter(headerAdapter, usersAdapter, footerAdapter)
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(requireContext())
            recycleView.setHasFixedSize(true)
            recycleView.addOnScrollListener(scrollListener)
            recycleView.adapter = concatAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        metricsStateHolder = null
        super.onDestroyView()
    }
}