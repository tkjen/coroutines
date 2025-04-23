package com.example.coroutines

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coroutines.databinding.ActivityMainBinding
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var count = 0;
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            requestData()

        }

        binding.btnCount.setOnClickListener{
            if(!isLoading){
                count++
                binding.tvData.text = "count:${count}"
            }
        }

    }

    private fun requestData() {
        Log.d("MainActivity", "requestData on ${Thread.currentThread().name}")
        Thread.sleep(2000L)
        binding.progressBar.visibility = ProgressBar.GONE
        isLoading = true
        binding.tvData.text = "Data from sever"
    }


}