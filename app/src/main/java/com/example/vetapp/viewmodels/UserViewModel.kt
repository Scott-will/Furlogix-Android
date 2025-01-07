package com.example.vetapp.viewmodels

import android.util.Log
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    val userName = userDao.getCurrentUserName().asFlow()
    val userEmail = userDao.getCurrentUserEmail().asFlow()

    private val TAG = "VetApp:" + ReportViewModel::class.qualifiedName

    var name by mutableStateOf("")
        private set
    var surname by mutableStateOf("")
        private set
    var petName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    // Error states for form validation
    var nameError by mutableStateOf<String?>(null)
        private set
    var surnameError by mutableStateOf<String?>(null)
        private set
    var petNameError by mutableStateOf<String?>(null)
        private set
    var emailError by mutableStateOf<String?>(null)
        private set

    fun updateUserProfile(name: String, email: String) {
        viewModelScope.launch {
            Log.d(TAG, "Updating user ${name}")
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
            Log.d(TAG, "Adding user ${user.name}")
            userDao.insert(user)
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

    fun onPetNameChange(newPetName: String) {
        petName = newPetName
        petNameError = null
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
        if (petName.isBlank()) {
            petNameError = "Pet Name cannot be empty"
            isValid = false
        }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Enter a valid email"
            isValid = false
        }

        return isValid
    }
}