package com.example.urbaneye

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.urbaneye.data.repository.IncidentRepository
import com.example.urbaneye.ui.screens.HomeScreen
import com.example.urbaneye.ui.screens.InfoScreen
import com.example.urbaneye.ui.screens.incidentSequence.EnterNameScreen
import com.example.urbaneye.ui.screens.incidentSequence.ReportIncidentScreen
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("enterName") { EnterNameScreen(viewModel = ReportIncidentViewModel(), repository = IncidentRepository(), onBack = { navController.popBackStack() }) }
        composable("reportIncident") { ReportIncidentScreen(viewModel = ReportIncidentViewModel(), repository = IncidentRepository(), onBack = { navController.popBackStack() }) }
        composable("info") { InfoScreen(onBack = { navController.popBackStack() }) }
        composable("cameras") { /* Заглушка */ }
    }
}