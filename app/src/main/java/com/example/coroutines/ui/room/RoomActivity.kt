package com.example.coroutines.ui.room

// import DatabaseHelperImpl // KHÔNG CẦN import này nữa
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels // Đảm bảo import này tồn tại
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines.data.local.entity.User
import com.example.coroutines.databinding.ActivityRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // Đúng
class RoomActivity : AppCompatActivity() {

    // Dòng này ĐÚNG - Hilt sẽ cung cấp ViewModel thông qua delegate này
    private val viewModel: RoomViewModel by viewModels()

    private lateinit var binding: ActivityRoomBinding

    // Lưu ý: việc quản lý ID thủ công này có thể không cần thiết
    // nếu bạn đặt id là khóa chính tự tăng trong Room Entity (@PrimaryKey(autoGenerate = true))
    private var userId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()

    }

    private fun setupClickListeners() {
        binding.btnInsert.setOnClickListener {
            // Nên dùng trim() và isNotEmpty() để kiểm tra input
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val avatar = binding.etAvatar.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && avatar.isNotEmpty()) {
                // Nếu ID tự tăng, bạn không cần truyền id:
                // val user = User(name = name, email = email, avatar = avatar)
                // Nếu ID quản lý thủ công:
                val user = User(id = userId++, name = name, email = email, avatar = avatar)

                // ViewModel lấy từ "by viewModels()" sẽ được sử dụng ở đây
                viewModel.insertUser(user)

                // Việc quản lý ProgressBar nên dựa trên trạng thái từ ViewModel (ví dụ: LiveData/Flow)
                binding.progressbar.visibility = View.VISIBLE

                // Xóa input sau khi thêm thành công
                binding.etName.text.clear()
                binding.etEmail.text.clear()
                binding.etAvatar.text.clear()

            } else {
                // Có thể thêm thông báo lỗi cho người dùng
            }
        }
    }

    /*
    private fun observeViewModel() {
        // Ví dụ observe trạng thái loading hoặc danh sách users từ ViewModel
        viewModel.isLoading.observe(this) { isLoading ->
             binding.progressbar.visibility = if(isLoading) View.VISIBLE else View.GONE
        }
        // viewModel.users.observe(this) { users -> ... }
    }
    */
}