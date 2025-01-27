package com.amb.stockmanagerapp.presentation.ui.utils.components

import ShimmerEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

@Composable
fun Image(data: String, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        loading = {
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
            )
        },
        contentDescription = null,
        modifier = modifier,
        alignment = Alignment.Center,
        model = data
    )
}