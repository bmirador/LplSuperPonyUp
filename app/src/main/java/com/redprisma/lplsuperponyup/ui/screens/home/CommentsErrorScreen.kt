package com.redprisma.lplsuperponyup.ui.screens.home

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
import com.redprisma.lplsuperponyup.data.util.AppError

@Composable
fun ErrorScreen(appError: AppError, loadComments: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val errorText = when(appError) {
            AppError.Network -> stringResource(R.string.there_is_a_network_error_we_are_sorry)
            AppError.NotFound -> stringResource(R.string.the_comments_that_you_are_looking_for_are_not_here)
            AppError.Server -> stringResource(R.string.generic_server_error_message)
            AppError.Timeout -> stringResource(R.string.its_taking_too_much_time_to_load)
            AppError.Unauthorized -> stringResource(R.string.you_are_not_authorized_to_access_this_resource)
            is AppError.Unknown -> stringResource(R.string.we_really_don_t_know_what_is_going_on_here_is_a_generic_message)
        }

        Text(errorText)
        Button(onClick = { loadComments.invoke() }) {
            Text(stringResource(R.string.retry))
        }
    }

}