package com.example.urbaneye.data.model

data class Incident(
    val fullName: String,
    val email: String,
    val phoneNumber: String? = null,
    val incidentDescription: String,
    val photoUris: List<String>,
    val isAnonymous: Boolean = false
)