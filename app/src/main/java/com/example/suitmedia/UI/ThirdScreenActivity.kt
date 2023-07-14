package com.example.suitmedia.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmedia.Adapter.LoadingAdapter
import com.example.suitmedia.Adapter.UserAdapter
import com.example.suitmedia.R
import com.example.suitmedia.UI.Other.MainViewModel
import com.example.suitmedia.UI.Other.ViewModelFactory
import com.example.suitmedia.databinding.ActivityThirdScreenBinding
import kotlinx.coroutines.launch

class ThirdScreenActivity : AppCompatActivity() {

    private var _activityThirdBinding: ActivityThirdScreenBinding? = null
    private val binding get() = _activityThirdBinding!!

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityThirdBinding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        val adapter = UserAdapter{
            val resultIntent = Intent()
            resultIntent.putExtra(SecondScreenActivity.EXTRA_SELECTED_NAME, "${it.firstName} ${it.lastName}")
            setResult(SecondScreenActivity.RESULT_OK, resultIntent)
            finish()
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect{
                when(it.refresh){
                    is LoadState.Loading -> { binding.progressThird.visibility = View.VISIBLE }
                    is LoadState.NotLoading -> {
                        binding.progressThird.visibility = View.GONE
                        if(it.append.endOfPaginationReached && adapter.itemCount < 1) {
                            Toast.makeText(this@ThirdScreenActivity, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
                        }
                    }
                    is LoadState.Error -> {
                        binding.progressThird.visibility = View.GONE
                        Toast.makeText(this@ThirdScreenActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                mainViewModel.getUser()
                isRefreshing = false
            }
        }

        binding.ivBack.setOnClickListener { finish() }

        binding.rvListUser.apply {
            this.layoutManager = layoutManager
            addItemDecoration(itemDecoration)
            this.adapter = adapter.withLoadStateFooter(footer = LoadingAdapter{adapter.retry()})
        }

        mainViewModel.listUser.observe(this){ adapter.submitData(lifecycle, it) }

    }

    override fun onDestroy() {
        super.onDestroy()
        _activityThirdBinding = null
    }
}
