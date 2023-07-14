package com.example.suitmedia.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.suitmedia.R
import com.example.suitmedia.UI.Other.MainViewModel
import com.example.suitmedia.UI.Other.ViewModelFactory
import com.example.suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _activityFirstBinding: ActivityMainBinding? = null
    private val binding get() = _activityFirstBinding!!

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFirstBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {
            val str = binding.palindromeMain.text.toString().trim()
            if(mainViewModel.isPalindrome(str)){
                Toast.makeText(this@MainActivity, getString(R.string.is_palindrome), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@MainActivity, getString(R.string.isnt_palindrome), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            val name = binding.nameMain.text.toString().trim()
            val intent = Intent(this@MainActivity, SecondScreenActivity::class.java)
            intent.putExtra(SecondScreenActivity.EXTRA_FIRST_NAME, name)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFirstBinding = null
    }
}