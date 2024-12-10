package com.example.vetapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    val userName = userDao.getCurrentUserName().asFlow()
    val userEmail = userDao.getCurrentUserEmail().asFlow()
    val userId: Flow<Long> = userDao.getCurrentUserId()

    var name by mutableStateOf("")
        private set
    var surname by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    // Error states for form validation
    var nameError by mutableStateOf<String?>(null)
        private set
    var surnameError by mutableStateOf<String?>(null)
        private set
    var emailError by mutableStateOf<String?>(null)
        private set

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

    fun addUser(user: User, onUserAdded: (Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userDao.insert(user)
            withContext(Dispatchers.Main) {
                onUserAdded(userId)
            }
        }
    }

    fun onNameChange(newName: String) {
        name = newName
        nameError = null
    }

    fun onSurnameChange(newSurname: String) {
        surname = newSurname
        surnameError = null
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailError = null
    }

    fun isFormValid(): Boolean {
        var isValid = true

        if (name.isBlank()) {
            nameError = "Name cannot be empty"
            isValid = false
        }
        if (surname.isBlank()) {
            surnameError = "Surname cannot be empty"
            isValid = false
        }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Enter a valid email"
            isValid = false
        }

        return isValid
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteUserById(userId)
        }
    }
}