package com.example.xml.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.model.User
import com.example.xml.R
import com.example.xml.databinding.ListItemBinding

typealias onListItemClickListener = (User, View?) -> Unit

class UsersAdapter(
    private val listener: onListItemClickListener
) : ListAdapter<User, RecyclerView.ViewHolder>(UserDiffCallback), View.OnClickListener {

    class UserViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                root.tag = user
                iv.transitionName =
                    root.resources.getString(R.string.shared_image_transition, user.id)
                tv.transitionName =
                    root.resources.getString(R.string.shared_name_transition, user.id)

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val user = currentList[position]
            holder.bind(user)
        }
    }

    override fun onClick(v: View?) {
        val user = v?.tag as User
        listener.invoke(user, v)
    }

    object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}
