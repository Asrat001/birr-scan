package com.itechsolution.birrscan.ui.home

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.itechsolution.birrscan.ui.componets.ActivityRow
import com.itechsolution.birrscan.ui.componets.CameraFab
import com.itechsolution.birrscan.ui.componets.QuickActions
import com.itechsolution.birrscan.ui.componets.RecentActivityHeader
import com.itechsolution.birrscan.ui.componets.RevenueCard
import com.itechsolution.birrscan.utils.TimeUtils.toHumanReadableDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenCamera: () -> Unit,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "BIRR SCAN",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Gray,
                        letterSpacing = 0.6.sp
                    )
                }
            )
        },
        floatingActionButton = {
            CameraFab(onClick = onOpenCamera)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF7F8FA))
                .padding(16.dp)
        ) {
            // Static content: Revenue and QuickActions
            RevenueCard(
                todayRevenue = state.totalAmount,
                totalTransactions = state.transactions.size
            )
            Spacer(modifier = Modifier.height(16.dp))
            QuickActions()
            Spacer(modifier = Modifier.height(16.dp))
            RecentActivityHeader(navController=navController)
            Spacer(modifier = Modifier.height(16.dp))
            // Scrollable transaction list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // take remaining space
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.transactions.take(3)) { transaction ->
                    ActivityRow(
                        title = transaction.institution ?: "Unknown",
                        time = transaction.timestamp.toHumanReadableDate(),
                        amount = transaction.amount ?: 0.0,
                        status = "success"
                    )
                }

                // Loading / error
                item {
                    when {
                        state.isLoading -> CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp)
                        )
                        state.error != null -> Text(
                            "Error: ${state.error}",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
