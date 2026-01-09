package com.itechsolution.birrscan.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.itechsolution.birrscan.ui.componets.ActivityRow
import com.itechsolution.birrscan.utils.TimeUtils.toHumanReadableDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen (viewModel: HomeViewModel = hiltViewModel()){

    val state by viewModel.uiState.collectAsState()

    return Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Transaction History", style = TextStyle(
                        fontWeight = FontWeight.W300,
                        fontSize = 18.sp,
                        letterSpacing = 0.15.sp,
                        color = Color.Gray.copy(alpha = 0.45f)
                    ))
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Filter",
                            tint = Color.Gray.copy(alpha = 0.45f)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Filter",
                            tint = Color.Gray.copy(alpha = 0.45f)
                        )
                    }
                }
            )
        },



    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            items(
                state.transactions,

            ) { transaction ->
                ActivityRow(
                    title = transaction.institution ?: "Unknown",
                    time = transaction.timestamp.toHumanReadableDate(),
                    amount = transaction.amount ?: 0.0,
                    status = "success"
                )
            }


        }


    }
}


