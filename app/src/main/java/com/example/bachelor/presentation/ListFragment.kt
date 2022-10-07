package com.example.bachelor.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class ListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {

        }
    }


/*    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        val adapter = UsersAdapter(listener = object : onListItemClickListener {
            override fun invoke(user: User, view: View?) {
                navigator().showDetails(user, view)
            }
        })
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(requireContext())
            recycleView.adapter = adapter
        }
        adapter.submitList(UserRepository.users)
        view.doOnPreDraw { startPostponedEnterTransition() }
    }*/
}