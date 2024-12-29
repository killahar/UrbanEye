package com.example.urbaneye.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.urbaneye.data.model.Incident
import com.example.urbaneye.data.repository.IncidentRepository

class ReportIncidentViewModel : ViewModel() {
    var description by mutableStateOf("")
        private set
    var photoUri by mutableStateOf<String?>(null)
        private set
    var phoneNumber by mutableStateOf("")
    var isAnonymous by mutableStateOf(false)
        private set

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun attachPhoto(uri: String) {
        photoUri = uri
    }

    fun toggleAnonymous() {
        isAnonymous = !isAnonymous
    }

    fun submitIncident(repository: IncidentRepository) {
        val incident = Incident(description, photoUri, if (isAnonymous) null else phoneNumber, isAnonymous)
        repository.addIncident(incident)
        // Reset state if needed
    }
}