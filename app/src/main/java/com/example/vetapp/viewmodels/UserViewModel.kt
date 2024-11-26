package com.example.vetapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.DAO.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    val userName = userDao.getCurrentUserName().asFlow()
    val userEmail = userDao.getCurrentUserEmail().asFlow()

    fun updateUserProfile(name: String, email: String) {
        viewModelScope.launch {
            userDao.updateUser(name, email) // Assuming updateUser is defined in your UserDao
        }
    }
}