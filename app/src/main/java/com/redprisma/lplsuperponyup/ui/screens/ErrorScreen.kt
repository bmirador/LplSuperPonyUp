package com.redprisma.lplsuperponyup.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.redprisma.lplsuperponyup.R

@Composable
fun ErrorScreen(string: String, loadComments: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.you_got_us_error, string))
        Button(onClick = { loadComments.invoke() }) {
            Text(stringResource(R.string.retry))
        }
    }

}