package com.amb.stockmanagerapp.presentation.product_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amb.stockmanagerapp.domain.model.Product
import com.amb.stockmanagerapp.presentation.ui.utils.components.Image

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    state: StockViewState,
    onItemClick: (Int) -> Unit
) {
    Box(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.data) { item ->
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                    colors = CardColors(
                        containerColor = Color.White,
                        contentColor = Color.White,
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                        .clickable(
                            onClick = { onItemClick.invoke(item.id) }
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(item.image, modifier = Modifier.size(150.dp))
                        ItemContent(item)
                    }
                }
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (state.error.isNotEmpty()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun ItemContent(item: Product) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            color = Color.Black,
            text = item.name,
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            color = Color.Black,
            text = "$${item.price}",
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.size(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
            Text(
                color = Color.Black,
                text = "${item.rating.rate} (${item.rating.count})",
                textAlign = TextAlign.Start
            )
        }
    }
}
