package com.sr.compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import clickWithDebounce
import com.sr.compose.MainViewModel
import com.sr.compose.navigation.NavigationItem


@Composable
@Preview(showBackground = true)
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: MainViewModel = viewModel(),
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(viewModel.items.value) {
            CardRow(item = it) { item ->
                when (item.text) {
                    "Detail: Logo" -> {
                        navController.navigate(NavigationItem.withRouteArgs(
                            navItem = NavigationItem.Detail,
                            arg = "You've just passed default arguments."))
                    }
                    "Detail: Jet" -> {}
                    "Detail: Ui" -> {}
                    "Detail: Material" -> {}
                }
            }
        }
    }
}

@Composable
fun CardRow(item: MainViewModel.ComposeItem, onItemClick: (MainViewModel.ComposeItem) -> Unit) {
    Card(modifier = Modifier
        .height(130.dp)
        .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
        .fillMaxWidth()
        .clickWithDebounce {

            onItemClick(item)

                           },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp) {
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = item.image),
                contentDescription = item.text,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                contentScale = ContentScale.FillHeight)
        }

        Text(text = item.text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth())
    }
}