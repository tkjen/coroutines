package com.example.coroutines.ui.room

// import DatabaseHelperImpl // KHÔNG CẦN import này nữa
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels // Đảm bảo import này tồn tại
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutines.data.local.entity.User
import com.example.coroutines.databinding.ActivityRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomActivity : AppCompatActivity() {


    private val viewModel: RoomViewModel by viewModels()
    private lateinit var binding: ActivityRoomBinding
    private var userId = 1
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        observeViewModel()
        setupClickListeners()

    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RoomActivity)
            adapter = userAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { users ->
            binding.progressbar.visibility = View.GONE
            userAdapter.setUsers(users)
        }
    }

    private fun setupClickListeners() {
        binding.btnInsert.setOnClickListener {
            // Nên dùng trim() và isNotEmpty() để kiểm tra input
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val avatar = binding.etAvatar.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && avatar.isNotEmpty()) {
                // Nếu ID tự tăng, bạn không cần truyền id:
                val user = User(name = name, email = email, avatar = avatar)
                // Nếu ID quản lý thủ công:
               // val user = User(id = userId++, name = name, email = email, avatar = avatar)

                // ViewModel lấy từ "by viewModels()" sẽ được sử dụng ở đây
                viewModel.insertUser(user)

                // Việc quản lý ProgressBar nên dựa trên trạng thái từ ViewModel (ví dụ: LiveData/Flow)
                binding.progressbar.visibility = View.VISIBLE

                // Xóa input sau khi thêm thành công
                binding.etName.text.clear()
                binding.etEmail.text.clear()
                binding.etAvatar.text.clear()



            } else {
                Toast.makeText(this, "Co loi gi do ", Toast.LENGTH_SHORT).show()
            }
        }
    }

}