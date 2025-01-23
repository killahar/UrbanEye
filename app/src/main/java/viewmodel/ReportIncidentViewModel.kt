package com.example.urbaneye.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.urbaneye.data.model.Incident
import com.example.urbaneye.data.repository.IncidentRepository

class ReportIncidentViewModel : ViewModel() {

    private val repository = IncidentRepository()

    var fullName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var incidentDescription by mutableStateOf("")
        private set
    var photoUris by mutableStateOf<List<String>>(emptyList())
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var isAnonymous by mutableStateOf(false)
        private set



    fun attachPhoto(uri: String) {
        if (photoUris.size < 10) { // Проверка на максимальное количество фотографий
            photoUris = photoUris + uri
        }
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
        photoUris = emptyList()
        phoneNumber = ""
        isAnonymous = false
    }
}