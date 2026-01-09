package com.itechsolution.birrscan.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itechsolution.birrscan.R



@Composable
fun CameraControls(
    onImageCaptured: () -> Unit,
    modifier: Modifier

){
    // 🔽 Bottom Controls Overlay
    Box(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🖼️ Gallery Button
            IconButton(
                onClick = { /* open gallery */ },
                modifier = Modifier.padding(end = 8.dp).background(Color.Gray.copy(alpha = 0.2f), shape = CircleShape)

            ) {
                Icon(
                    painter =painterResource(id = R.drawable.flash_1_svgrepo_com),
                    contentDescription = "Gallery",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }

            // ⭕ Capture Button
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
                    .background(
                        Color.Green,
                        shape = CircleShape
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                        .then(
                            Modifier
                                .fillMaxSize()
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )
                        .clickable {
                            onImageCaptured()
                        }
                )
            }

            // 🧾 Receipt / History Button
            IconButton(onClick = { /* open receipts */ },
                modifier = Modifier.padding(start = 4.dp).background(color = Color.Gray.copy(alpha = 0.2f), shape = CircleShape)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.scan_qrcode_svgrepo_com),
                    contentDescription = "Receipts",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}