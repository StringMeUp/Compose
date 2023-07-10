package com.sr.compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sr.compose.MainViewModel
import com.sr.compose.clickWithDebounce
import com.sr.compose.navigation.ComposeItem


@Composable
@Preview(showBackground = true)
fun HomeScreen(
    viewModel: MainViewModel = viewModel(),
    handleOnItemClick: (item: ComposeItem) -> Unit = {},
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 70.dp)) {
        items(viewModel.items.value) {
            CardRow(item = it) { item ->
                handleOnItemClick(item)
            }
        }
    }
}

@Composable
fun CardRow(item: ComposeItem, onItemClick: (ComposeItem) -> Unit) {
    Card(modifier = Modifier
        .height(130.dp)
        .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
        .fillMaxWidth()
        .clickWithDebounce {
            onItemClick(item)
        },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (image, text) = createRefs()
            Image(
                painterResource(id = item.image),
                contentDescription = item.text,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentScale = ContentScale.FillHeight)

            Text(
                text = item.text,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .constrainAs(text) {
                        start.linkTo(image.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
            )
        }
    }
}