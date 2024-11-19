package com.example.vetapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {

    fun addUser(name: String, surname: String, petName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(name = name, surname = surname, petName = petName, email = email)
            userDao.insert(user)
        }
    }
}