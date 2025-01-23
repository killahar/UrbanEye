package com.example.urbaneye

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.urbaneye.ui.screens.HomeScreen
import com.example.urbaneye.ui.screens.InfoScreen
import com.example.urbaneye.ui.screens.incidentSequence.AttachPhotoScreen
import com.example.urbaneye.ui.screens.incidentSequence.ConfirmDataScreen
import com.example.urbaneye.ui.screens.incidentSequence.EnterEmailScreen
import com.example.urbaneye.ui.screens.incidentSequence.EnterNameScreen
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ReportIncidentViewModel = viewModel()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }

        composable("enterName") {
            EnterNameScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate("enterEmail") }
            )
        }

        composable("enterEmail") {
            EnterEmailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate("attachPhoto") }
            )
        }

        composable("attachPhoto") {
            AttachPhotoScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate("confirmData") }
            )
        }

        composable("confirmData") {
            ConfirmDataScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSubmit = {
                    navController.popBackStack("home", inclusive = false)
                    navController.navigate("home")
                }
            )
        }

        composable("info") { InfoScreen(onBack = { navController.popBackStack() }) }
    }
}
