package com.example.urbaneye.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.urbaneye.data.model.Incident
import com.example.urbaneye.data.repository.IncidentRepository

class ReportIncidentViewModel : ViewModel() {

    private val repository = IncidentRepository()
    private val _photoUris = mutableStateListOf<String>()

    var fullName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
    var incidentDescription by mutableStateOf("")
    var photoUris: List<String> by mutableStateOf(emptyList())
        private set
    var phoneNumber by mutableStateOf("")
    var isAnonymous by mutableStateOf(false)
        private set

    fun attachPhoto(uri: String) {
        if (_photoUris.size < 10) {
            _photoUris.add(uri)
            updatePhotoUris()
        }
    }

    fun removePhoto(uri: String) {
        _photoUris.remove(uri)
        updatePhotoUris()
    }

    private fun updatePhotoUris() {
        photoUris = _photoUris.toList()
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
    }

    fun updateIncidentDescription(newIncidentDescription: String) {
        incidentDescription = newIncidentDescription
    }

    fun toggleAnonymous() {
        isAnonymous = !isAnonymous
    }

    fun updateName(surname: String, name: String, patronymic: String) {
        fullName = "$surname $name $patronymic".trim()
    }

    fun submitIncident() {
        val incident = Incident(
            fullName = fullName,
            email = email,
            incidentDescription = incidentDescription,
            photoUris = photoUris,
            phoneNumber = if (isAnonymous) null else phoneNumber,
            isAnonymous = isAnonymous
        )
        repository.addIncident(incident)
        resetState()
    }

    private fun resetState() {
        fullName = ""
        email = ""
        incidentDescription = ""
        _photoUris.clear()
        phoneNumber = ""
        isAnonymous = false
    }
}