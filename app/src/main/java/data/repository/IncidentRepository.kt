package com.example.urbaneye.data.repository

import com.example.urbaneye.data.model.Incident

class IncidentRepository {
    private val incidents = mutableListOf<Incident>()

    fun addIncident(incident: Incident) {
        incidents.add(incident)
    }

    fun getIncidents(): List<Incident> = incidents
}