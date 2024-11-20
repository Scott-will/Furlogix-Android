package com.example.vetapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.DAO.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    fun doesUserExist(onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = userDao.countUsers() > 0
            onResult(userExists)
        }
    }
}