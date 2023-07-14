package com.example.suitmedia.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmedia.Response.User
import com.example.suitmedia.databinding.UserItemBinding

class UserAdapter(private val onClick: (User) -> Unit) : PagingDataAdapter<User, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            "${data.firstName} ${data.lastName}".also { holder.binding.tvNameUser.text = it }
            holder.binding.tvEmailUser.text = data.email
            Glide.with(holder.itemView)
                .load(data.avatar)
                .into(holder.binding.ivItemPhoto)
            holder.itemView.setOnClickListener { onClick(data) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


}