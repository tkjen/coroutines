
package com.example.coroutines.ui.base
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines.R
import com.example.coroutines.databinding.ActivityMainBinding
import com.example.coroutines.ui.retrofit.SingleNetworkCallActivity
import com.example.coroutines.ui.room.RoomActivity
import dagger.hilt.android.HiltAndroidApp

class BasicActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBasic.setOnClickListener {
            startActivity(Intent(this, BasicActivity::class.java))
        }
        binding.btnRoom.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }
        binding.btnCallNetwork.setOnClickListener{
            startActivity(Intent(this, SingleNetworkCallActivity::class.java))
        }

    }

}
