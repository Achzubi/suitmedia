package com.example.suitmedia.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.suitmedia.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {

    private var _activitySecondBinding: ActivitySecondScreenBinding? = null
    private val binding get() = _activitySecondBinding!!

    val launcherThirdActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            val selectedUser = it.data?.getStringExtra(EXTRA_SELECTED_NAME) ?: ""
            binding.tvNameSecond.text = selectedUser
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activitySecondBinding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstName = intent.getStringExtra(EXTRA_FIRST_NAME) ?: ""
        binding.tvNameSecond.text = firstName

        binding.ivBack.setOnClickListener { finish() }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this@SecondScreenActivity, ThirdScreenActivity::class.java)
            launcherThirdActivity.launch(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activitySecondBinding = null
    }

    companion object{
        const val EXTRA_FIRST_NAME = "extra_first_name"
        const val EXTRA_SELECTED_NAME = "extra_selected_name"
        const val RESULT_OK = 200
    }
}