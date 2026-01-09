package com.itechsolution.birrscan.ui.camera

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.itechsolution.birrscan.ui.componets.InAppCamera
import com.itechsolution.birrscan.ui.verification.VerificationViewModel
import com.itechsolution.birrscan.utils.Screen

enum class Bank {
    CBE,
    TELEBIRR
}


@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: VerificationViewModel = hiltViewModel()
) {
    var selectedBank by remember { mutableStateOf<Bank?>(null) }

    Scaffold() {  padding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        )
        {
            Text("Select Bank", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.W300,
                letterSpacing = 0.15.sp,
                color = Color.Gray.copy(alpha = 0.45f)

            ))
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    selectedBank = Bank.CBE

                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,      // background color
                    contentColor = Color.Black        // text/icon color
                ),
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(8.dp),
                content = {
                    Text(
                        text = "CBE", style = TextStyle(
                            fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.W300
                        )
                    )
                }

            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    selectedBank = Bank.TELEBIRR

                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF22C55E),      // background color
                    contentColor = Color.White        // text/icon color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(8.dp),
                content = {
                    Text(
                        text = "Tele Birr", style = TextStyle(
                            fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W300
                        )
                    )
                }


            )

        }

        // Only enable camera if bank selected
        if (selectedBank != null) {
            InAppCamera { file ->
                // file contains captured image
                // Navigate to Verification screen, passing image path
                navController.navigate(
                    Screen.Verification.createRoute(file.absolutePath, selectedBank.toString())
                )

            }
        }
    }

}