package com.example.vetapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetapp.Database.Entities.Pet
import com.example.vetapp.repositories.IPetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val petRepository: IPetRepository
) : ViewModel() {
    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets.asStateFlow()
    private val _photoUri = MutableStateFlow<String?>(null)
    val photoUri: StateFlow<String?> = _photoUri.asStateFlow()

    var name = MutableStateFlow("")
    var type = MutableStateFlow("")
    var description = MutableStateFlow("")

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    var petNameError by mutableStateOf<String?>(null)
        private set

    fun loadPetsForUser(userId: Long) {
        viewModelScope.launch {
            val userPets = petRepository.getPetsForUser(userId)
            _pets.value = userPets
        }
    }

    fun savePetPhotoUri(uri: String) {
        _photoUri.value = uri
    }

    fun addPet(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val pet = Pet(
                name = name.value,
                type = type.value.ifEmpty { "Unknown" },
                description = description.value.ifEmpty { "No description" },
                userId = userId
            )
            petRepository.addPet(pet)
        }
    }

    fun addPetObj(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.addPet(pet)
        }
    }

    fun validateForm(): Boolean {
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

    fun deletePet(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.deletePet(pet)
            val updatedPets = petRepository.getPetsForUser(pet.userId)
            _pets.value = updatedPets
        }
    }



}