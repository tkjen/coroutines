package com.example.coroutines.ui.retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutines.databinding.ActivitySinglenetworkcallBinding
import com.example.coroutines.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleNetworkCallActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySinglenetworkcallBinding
    private val viewModel: SingleNetworkCallViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    private val TAG = "SingleNewWorkCall_Logs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinglenetworkcallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewUsers.adapter = adapter

        viewModel.fetchUsers()

        lifecycleScope.launch {
            viewModel.users.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressbar.visibility = View.GONE
                        val users = resource.data ?: emptyList()
                        Log.d(TAG, "Số lượng user: ${users.size}")
                        adapter.setData(users)
                    }
                    is Resource.Error -> {
                        binding.progressbar.visibility = View.GONE
                        Toast.makeText(this@SingleNetworkCallActivity, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}