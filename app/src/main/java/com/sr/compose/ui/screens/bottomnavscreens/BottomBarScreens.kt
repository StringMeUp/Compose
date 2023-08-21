package com.sr.compose.ui.screens.bottomnavscreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ui.BottomNavigation
import com.sr.compose.R
import com.sr.compose.navigation.NavigationItem

@Composable
@Preview(showBackground = true)
fun BottomHome() {
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .clickable(onClick = { }) /*question = "3 Bananas required"*/
            .clip(shape = RoundedCornerShape(16.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 4.dp, color = Gray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bottom 1",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = typography.h4,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomTwo() {
    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .clickable(onClick = { }) /*question = "3 Bananas required"*/
            .clip(shape = RoundedCornerShape(16.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 4.dp, color = Gray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bottom 2",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = typography.h4,
            )
        }
    }
}