package com.itechsolution.birrscan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itechsolution.birrscan.ui.camera.CameraScreen
import com.itechsolution.birrscan.ui.verification.VerificationScreen
import com.itechsolution.birrscan.ui.home.HomeScreen
import com.itechsolution.birrscan.ui.home.TransactionListScreen
import com.itechsolution.birrscan.utils.Screen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                onOpenCamera = { navController.navigate(Screen.Camera.route) }
            )
        }
        composable(Screen.Camera.route) {
            CameraScreen(
                navController = navController,

            )
        }

        composable(
            route = Screen.Verification.route,
            arguments = listOf(
                navArgument("imagePath") {
                    type = NavType.StringType
                },
               navArgument("bank") {
                   type = NavType.StringType
               }
            )
        ) {
            VerificationScreen(
                navController=navController
            )
        }
        composable(Screen.TransactionList.route) {
             TransactionListScreen()
        }

    }
}