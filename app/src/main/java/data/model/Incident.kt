package com.example.urbaneye.data.model

data class Incident(
    val description: String,
    val photoUri: String? = null,
    val phoneNumber: String? = null,
    val isAnonymous: Boolean = false
)