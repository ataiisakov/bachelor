package com.example.bachelor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.databinding.ListItemBinding
import com.example.bachelor.model.User

typealias onListItemClickListener = (User, View?) -> Unit

class UsersAdapter(
    private val listener: onListItemClickListener
): ListAdapter<User,UsersAdapter.UserViewHolder>(UserDiffCallback), View.OnClickListener {

    class UserViewHolder(
        private val binding: ListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            with(binding){
                root.tag = user
                root.transitionName = String.format(
                    root.resources.getString(R.string.shared_container_transition),
                    user.id
                )
                userPhotoImageView.load(user.photoUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_round_account_circle_56)
                }
                userNameTextView.text = user.name
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        binding.root.setOnClickListener(this)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun onClick(v: View?) {
        val user = v?.tag as User
        listener.invoke(user, v)
    }

}
object UserDiffCallback: DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}