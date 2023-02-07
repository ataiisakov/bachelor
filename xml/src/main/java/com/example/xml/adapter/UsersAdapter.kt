package com.example.xml.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.model.User
import com.example.xml.R
import com.example.xml.databinding.ListItemBinding

typealias onListItemClickListener = (User) -> Unit

class UsersAdapter(
    private val users: List<User>,
    private val listener: onListItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    class UserViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                root.tag = user

                iv.load(user.photoUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_round_account_circle_56)
                }
                tv.text = user.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        binding.root.setOnClickListener(this)
        return UserViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val user = users[position]
            holder.bind(user)
        }
    }

    override fun onClick(v: View?) {
        val user = v?.tag as User
        listener.invoke(user)
    }
}
