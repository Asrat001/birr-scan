package com.itechsolution.birrscan.ui.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionBottomSheet(
    initialAmount: Double?,
    initialTransactionId: String?,
    onDismiss: () -> Unit,
    onSave: (Double, String?) -> Unit
) {
    var amount by remember {
        mutableStateOf(initialAmount?.toString().orEmpty())
    }

    var transactionId by remember { mutableStateOf(initialTransactionId.orEmpty()) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Edit Transaction",
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Amount
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount (ETB)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Transaction ID (optional)
            OutlinedTextField(
                value = transactionId,
                onValueChange = { transactionId = it },
                label = { Text("Transaction ID (optional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF22C55E),      // background color
                    contentColor = Color.White        // text/icon color
                ),
                onClick = {
                    val parsedAmount = amount.toDoubleOrNull()
                    if (parsedAmount != null) {
                        onSave(parsedAmount, transactionId)
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save",style = TextStyle(
                    fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W300
                ))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
