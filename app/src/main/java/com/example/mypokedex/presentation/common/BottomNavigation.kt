package com.example.mypokedex.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypokedex.R

@Composable
fun BottomNavigation(
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {},
    modifier: Modifier
) {
    val items = listOf(
        bottomNav("Home", R.drawable.icon_home_active, R.drawable.icon_home_disable),
        bottomNav("Search", R.drawable.search, R.drawable.search),
        bottomNav("favourite", R.drawable.icon_fav_active, R.drawable.icon_fav_disable),

        )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(24.dp)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                BottomIcons(
                    icon = if (isSelected) item.iconActive else item.iconDisable,
                    contentDescription = item.name,
                    name = item.name,
                    isSelected = isSelected,
                    onClick = {
                       onItemSelected(index)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomIcons(
    icon: Int,
    contentDescription: String = "",
    name: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val contentColor = if (isSelected) Color(0xFF173EA4) else Color(0xFF676767)

    Surface(
        onClick = onClick,
        modifier = Modifier.size(58.dp),
        enabled = true,
        shape = androidx.compose.foundation.shape.CircleShape,
        color = Color.Transparent,
        contentColor = contentColor,
        tonalElevation = 0.dp,
        border = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(2.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
                fontSize = 9.sp
            )
        }
    }
}

data class bottomNav(
    val name: String,
    val iconActive: Int,
    val iconDisable: Int
)

@Preview()
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(modifier = Modifier)
}

@Preview()
@Composable
fun BottomIconsPreview() {
    BottomIcons(
        icon = R.drawable.icon_home_active,
        contentDescription = "Home",
        name = "Home"
    )
}