package com.sr.compose.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.sr.compose.R
import kotlinx.coroutines.launch

@Composable
fun BottomSheetScreen() {

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet() {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        }, sheetState = SheetState(true)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = colorResource(id = R.color.w_bg)
        ) {
            Column() {
                Button(modifier = Modifier.wrapContentSize(), onClick = {
                    scope.launch { sheetState.hide() }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }
    }
}