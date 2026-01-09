package com.itechsolution.birrscan.ui.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
 fun ActionButton(title: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)

    ) {
        Box(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(title, fontWeight = FontWeight.Medium)
        }
    }
}