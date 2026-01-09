package com.itechsolution.birrscan.ui.verification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.itechsolution.birrscan.R
import com.itechsolution.birrscan.ui.componets.EditTransactionBottomSheet
import com.itechsolution.birrscan.utils.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    navController: NavController,
    viewModel: VerificationViewModel = hiltViewModel()
) {
    var showEditSheet by remember { mutableStateOf(false) }


    val state = viewModel.uiState

    // Observe navigation event
    if (viewModel.navigateBackToHome) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Home.route) // Navigate back to Home
            viewModel.onNavigated()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Verify ", style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.Gray,
                            letterSpacing = 0.6.sp
                        )
                    )
                })
        }) {

            padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (state.isProcessing) {
                CircularProgressIndicator()
            }
            if (state.isVerified) {

                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(all = 4.dp)
                        .background(
                            color = Color.Green.copy(alpha = 0.3f), shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.scan_qrcode_svgrepo_com),
                        contentDescription = "scan"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "DETECTED AMOUNT", style = TextStyle(
                        fontWeight = FontWeight.W500,
                        color = Color.Gray.copy(alpha = 0.5f),
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = state.transactionData?.amount.toString(), style = TextStyle(
                            fontSize = 32.sp, color = Color.Black, fontWeight = FontWeight.W500
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "ETB", style = TextStyle(
                            fontSize = 18.sp, color = Color.Gray.copy(alpha = 0.4f)
                        )

                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .background(
                            color = Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Row() {
                        Icon(
                            painter = painterResource(id = R.drawable.info_svgrepo_com),
                            contentDescription = "info",
                            tint = Color.Gray.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column() {
                            Text(
                                text = "Please verify the amount before saving", style = TextStyle(
                                    fontSize = 16.sp, color = Color.Red.copy(alpha = 0.25f)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Provider: ${state.transactionData?.institution}",
                                style = TextStyle(
                                    fontSize = 16.sp, color = Color.Gray.copy(alpha = 0.5f)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Transaction ID: ${state.transactionData?.transactionId}",
                                style = TextStyle(
                                    fontSize = 16.sp, color = Color.Gray.copy(alpha = 0.5f)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,      // background color
                        contentColor = Color.Black        // text/icon color
                    ),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { showEditSheet = true },
                    content = {
                        Text(
                            text = "Edit Amount", style = TextStyle(
                                fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.W300
                            )
                        )
                    }

                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF22C55E),      // background color
                        contentColor = Color.White        // text/icon color
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { viewModel.saveTransaction() },
                    content = {
                        Text(
                            text = "Conform And Save", style = TextStyle(
                                fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W300
                            )
                        )
                    }


                )
            }
            if (state.error != null) {
                Text(
                    text = state.error, style = TextStyle(
                        fontSize = 18.sp, color = Color.Red, fontWeight = FontWeight.W300
                    )
                )
            }


        }
        if (showEditSheet && state.transactionData != null) {
            EditTransactionBottomSheet(
                initialAmount = state.transactionData?.amount,
                initialTransactionId = state.transactionData.transactionId,
                onDismiss = { showEditSheet = false },
                onSave = { amount, transactionId ->
                    viewModel.updateTransaction(amount, transactionId)
                }
            )
        }

    }
}