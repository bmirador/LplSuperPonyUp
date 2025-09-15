package com.redprisma.lplsuperponyup.ui.util

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.data.AssetPathState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Modifier.longClickForMock(assetPathState: AssetPathState): Modifier {
    val context = LocalContext.current

    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
    var showMockDialog by remember { mutableStateOf(false) }
    var listOfMocks by remember { mutableStateOf(listOf<String>()) }


    LaunchedEffect(true) {
        listOfMocks = context.mockJsonList()
    }

    val scope = rememberCoroutineScope()
    if (showMockDialog) {
        Dialog(onDismissRequest = { showMockDialog = false }) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White)
            ) {
                LazyColumn {
                    items(items = listOfMocks) {
                        Text(
                            modifier = Modifier.clickable {
                                scope.launch { assetPathState.setPath(it) }
                            }.padding(16.dp),
                            text = it,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }

    return this.combinedClickable(
        interactionSource = interactionSource,
        indication = null,
        onLongClick = {
            showMockDialog = true
        },
        onDoubleClick = {
            showMockDialog = true
        },
        onClick = {})
}

suspend fun Context.mockJsonList(): List<String> =
    withContext(Dispatchers.IO) {
        val dir = "mock"
        assets.list(dir)
            ?.filter { it.endsWith(".json") }
            ?.map { "$dir/$it" }
            .orEmpty()
    }
