package com.example.xml.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.bachelor.model.User
import com.example.xml.R
import com.example.xml.databinding.FooterListItemBinding
import com.example.xml.databinding.HeaderListItemBinding
import com.example.xml.databinding.ListItemBinding

typealias onListItemClickListener = (User) -> Unit

class UsersAdapter(
    private val users: List<User>,
    private val listener: onListItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    companion object {
        private const val HEADER_TYPE = 0
        private const val LIST_ITEM_TYPE = 1
        private const val FOOTER_TYPE = 2
    }

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
                tv.text = user.id.toString()
            }
        }
    }

    class HeaderViewHolder(
        private val binding: HeaderListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.headerTv.text = "Header Text"
        }
    }

    class FooterViewHolder(
        private val binding: FooterListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.headerTv.text = "Footer Text"
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            isHeaderPosition() -> HEADER_TYPE
            isFooterPosition() -> FOOTER_TYPE
            else -> LIST_ITEM_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER_TYPE -> {
                val binding = HeaderListItemBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }
            LIST_ITEM_TYPE -> {
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                binding.root.setOnClickListener(this)
                return UserViewHolder(binding)
            }
            FOOTER_TYPE -> {
                val binding = FooterListItemBinding.inflate(layoutInflater, parent, false)
                return FooterViewHolder(binding)
            }
            else -> throw RuntimeException("$viewType - viewType doesn't exist")
        }
    }

    override fun getItemCount() = users.size + 2

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                val user = users[position - 1]
                holder.bind(user)
            }
            is FooterViewHolder -> {
                holder.bind()
            }
            is HeaderViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun onClick(v: View?) {
        val user = v?.tag as User
        listener.invoke(user)
    }

    private fun isHeaderPosition() = 0
    private fun isFooterPosition() = itemCount - 1
}