package com.furlogix.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.furlogix.Database.Entities.User
import com.furlogix.email.EmailValidator
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val logger : ILogger, private val userRepository: IUserRepository) : ViewModel() {
    private var _currentUser = MutableStateFlow<User?>(null);
    val currentUser : StateFlow<User?> = _currentUser

    val userName = userRepository.getCurrentUserName().asFlow()
    val userEmail = userRepository.getCurrentUserEmail().asFlow()
    val userId: Flow<Long> = userRepository.getCurrentUserIdAsFlow()

    private val TAG = "Furlogix:" + ReportViewModel::class.qualifiedName

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
            Log.d(TAG, "Updating user ${name}")
            userRepository.updateUser(name, email)
        }
    }

    fun populateCurrentUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _currentUser.value = userRepository.getUserById(1)
            }
        }
    }

    fun doesUserExist(onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userExists = userRepository.countUsers() > 0
            onResult(userExists)
        }
    }

    fun addUser(user: User, onUserAdded: (Long) -> Unit) {
        if(!this.validateUser(user.name, user.surname, user.email)){
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            logger.log(TAG, "Adding user ${user.name}")
            val userId = userRepository.insert(user)
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
        return this.validateUser(this.name, this.surname, this.email)
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUserById(userId)
        }
    }

    fun setNoPendingReportsForUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                userRepository.setNoPendingReportsForUser(1)
                populateCurrentUser()
            }
        }
    }

    fun setPendingReportsForUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                userRepository.setPendingReportsForUser(1)
                populateCurrentUser()
            }
        }
    }

    fun validateUser(name : String,
                     surname : String,
                     email : String) : Boolean{
        var isValid = true

        if (name.isBlank()) {
            nameError = "Name cannot be empty"
            isValid = false
        }
        if (surname.isBlank()) {
            surnameError = "Surname cannot be empty"
            isValid = false
        }
        if (!EmailValidator.ValidateEmail(email)) {
            emailError = "Enter a valid email"
            isValid = false
        }

        return isValid
    }
}