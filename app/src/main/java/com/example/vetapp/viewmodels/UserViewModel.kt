package com.example.vetapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun doesUserExist(onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = userDao.countUsers() > 0
            onResult(userExists)
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }
}