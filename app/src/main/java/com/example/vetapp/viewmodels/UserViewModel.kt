package com.example.vetapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.example.vetapp.Database.DAO.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    val userName = userDao.getCurrentUserName().asFlow()
}