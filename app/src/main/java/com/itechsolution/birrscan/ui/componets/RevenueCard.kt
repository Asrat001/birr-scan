package com.itechsolution.birrscan.ui.componets
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
 fun RevenueCard(
     todayRevenue: Double = 0.0,
     totalTransactions: Int = 0

 ) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF22C55E)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Today's Revenue", color = Color.White.copy(alpha = 0.9f))
            Spacer(Modifier.height(8.dp))
            Text(
                text = "ETB   $todayRevenue",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(16.dp,))
            Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color.LightGray))
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Metric("Transactions", "$totalTransactions")
                Metric("Avg Order", "ETB 800")
            }
        }
    }
}