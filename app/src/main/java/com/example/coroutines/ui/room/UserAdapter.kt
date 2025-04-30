package com.example.coroutines.ui.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutines.R
import com.example.coroutines.data.local.entity.User

class UserAdapter :RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val users = mutableListOf<User>()

    fun setUsers(newUsers: List<User>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val tvName: TextView = itemView.findViewById(R.id.tvName)
         val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
            val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false) // dùng layout của bạn
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.tvName.text = user.name
        holder.tvEmail.text = user.email
        holder.tvAvatar.text = user.avatar
    }

    override fun getItemCount(): Int = users.size
}