package com.itechsolution.birrscan.utils

import android.net.Uri

sealed class Screen(val route:String) {
    object Home: Screen("home")
    object Camera:Screen("camera")
    object TransactionList:Screen("transactionList")
    object Verification : Screen("verification/{imagePath}/{bank}") {
        fun createRoute(imagePath: String,   bank: String): String =
            "verification/${Uri.encode(imagePath)}/${bank}"
    }
}