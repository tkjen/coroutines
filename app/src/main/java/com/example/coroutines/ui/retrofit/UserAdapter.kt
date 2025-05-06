package com.example.coroutines.ui.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutines.R
import com.example.coroutines.data.model.ApiUser

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users = mutableListOf<ApiUser>()

    fun setData(newUsers: List<ApiUser>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: ApiUser) {
            itemView.findViewById<TextView>(R.id.tvName).text = user.name
            itemView.findViewById<TextView>(R.id.tvEmail).text = user.email
            itemView.findViewById<TextView>(R.id.tvAvatar).text = user.avatar
//            Glide.with(itemView.context)
//                .load(user.avatar)
//                .into(itemView.findViewById(R.id.imageViewAvatar))
        }
    }
}
