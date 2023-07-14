package com.example.suitmedia.UI.Other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.suitmedia.Data.Repository
import com.example.suitmedia.Response.User
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var _listUser = MutableLiveData<PagingData<User>>()
    val listUser : LiveData<PagingData<User>>
        get() = _listUser

    init {
        getUser()
    }

    fun isPalindrome(str: String): Boolean {
        val input = str.lowercase().replace(" ", "")
        val length = input.length
        for (i in 0 until length / 2) {
            if (input[i] != input[length - i - 1]) {
                return false
            }
        }
        return true
    }

    fun getUser(){
        viewModelScope.launch {
            repository.getUsers().cachedIn(viewModelScope).collect{
                _listUser.value = it
            }
        }
    }

}