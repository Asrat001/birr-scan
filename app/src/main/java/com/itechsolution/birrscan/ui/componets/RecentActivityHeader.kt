package com.itechsolution.birrscan.ui.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.itechsolution.birrscan.ui.home.HomeViewModel
import com.itechsolution.birrscan.utils.Screen

@Composable
 fun RecentActivityHeader(
     navController: NavController
 ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Recent Activity", fontWeight = FontWeight.Bold)
        TextButton (onClick = {
            navController.navigate(Screen.TransactionList.route)
        }, content = {
            Text(text = "View All", color = Color(0xFF22C55E), fontSize = 12.sp)
        })
    }
}


@Composable
fun ActivityRow(
    title: String,
    time: String,
    amount: Double,
    status: String
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(time, fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("ETB $amount", fontWeight = FontWeight.Bold)
                Text(status, fontSize = 12.sp, color = Color(0xFF22C55E))
            }
        }
    }
}
