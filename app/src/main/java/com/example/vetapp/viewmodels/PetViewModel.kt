package com.example.vetapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.DAO.PetDao
import com.example.vetapp.Database.Entities.Pet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val petDao: PetDao) : ViewModel() {
    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets.asStateFlow()

    var name = MutableStateFlow("")
    var type = MutableStateFlow("")
    var description = MutableStateFlow("")

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    var petNameError by mutableStateOf<String?>(null)
        private set

    fun loadPetsForUser(userId: Long) {
        viewModelScope.launch {
            val petList = petDao.getPetsForUser(userId)
            _pets.value = petList
        }
    }

    fun addPet(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val pet = Pet(
                name = name.value,
                type = type.value.ifEmpty { "Unknown" },
                description = description.value.ifEmpty { "No description" },
                userId = userId
            )
            petDao.insert(pet)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (name.value.isBlank()) {
            _nameError.value = "Pet name cannot be empty"
            isValid = false
        } else {
            _nameError.value = null
        }

        return isValid
    }

    fun clearFields() {
        name.value = ""
        type.value = ""
        description.value = ""
    }


}