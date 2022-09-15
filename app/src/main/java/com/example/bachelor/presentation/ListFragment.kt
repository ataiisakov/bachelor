package com.example.bachelor.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bachelor.UsersAdapter
import com.example.bachelor.databinding.ListFragmentBinding
import com.example.bachelor.model.User
import com.example.bachelor.model.UserRepository
import com.example.bachelor.onListItemClickListener

class ListFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: ListFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        val adapter = UsersAdapter(listener = object : onListItemClickListener{
            override fun invoke(user: User) {
                navigator().showDetails(user)
            }
        })
        with(binding){
            recycleView.layoutManager = LinearLayoutManager(requireContext())
            recycleView.adapter = adapter
        }
        adapter.submitList(UserRepository.users)
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