package com.redprisma.lplsuperponyup.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.redprisma.lplsuperponyup.HomeState
import com.redprisma.lplsuperponyup.R
import com.redprisma.lplsuperponyup.data.models.Comment
import com.redprisma.lplsuperponyup.data.util.AppError
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CommentListScreen(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    loadComments: () -> Unit
) {
    // Render different UI based on the current state
    when (homeState) {
        is HomeState.Initial ->
            // Shown if something unexpected happened before loading
            Text(stringResource(R.string.if_you_are_reading_this_something_somewhere_went_really_wrong))

        is HomeState.Loading ->
            // Loading indicator centered on the screen
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .testTag(stringResource(R.string.loadingIndicator_testing)),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

        is HomeState.Success ->
            // Display list of comments when data is successfully loaded
            Column(
                modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (homeState.comments.isEmpty()) {
                    Text("No comments were found")
                } else {
                    if (homeState.fromCache) {
                        Text("Showing results from cache, there was a problem retrieving the latest comments")
                    }
                    LazyColumn {
                        items(
                            items = homeState.comments,
                            key = { it?.id ?: Uuid.random() }) { comment ->
                            comment?.run {
                                // Display individual comment item
                                CommentItem(
                                    id = id,
                                    name = name,
                                    email = email,
                                    body = body
                                )
                            }
                        }
                    }
                }
            }

        is HomeState.Error ->
            // Display error screen with retry option
            ErrorScreen(homeState.appError, loadComments)
    }
}


@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    id: Int,
    name: String,
    email: String,
    body: String
) {
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
        }
    }

    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Top)
                .clip(CircleShape)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = stringResource(R.string.user_icon),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.user_icon),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = email, style = MaterialTheme.typography.bodySmall)
            }

            Text(
                text = stringResource(R.string.id, id),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = body, style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CommentLoadingPreview() {
    CommentListScreen(
        homeState = HomeState.Loading,
    ) { }
}

@Preview(showBackground = true)
@Composable
private fun CommentSuccessPreview() {
    CommentListScreen(
        homeState = HomeState.Success(
            listOf(
                Comment(
                    postId = 1,
                    id = 1,
                    name = "id labore ex et quam laborum",
                    email = "Eliseo@gardner.biz",
                    body = "laudantium enim quasi est quidem magnam voluptate ipsam eos\n" +
                            "tempora quo necessitatibus\n" +
                            "dolor quam autem quasi\n" +
                            "reiciendis et nam sapiente accusantium"
                ),
                Comment(
                    1,
                    2,
                    "quo vero reiciendis velit similique earum",
                    "Jayne_Kuhic@sydney.com",
                    "est natus enim nihil est dolore omnis voluptatem numquam\n" +
                            "et omnis occaecati quod ullam at\n" +
                            "voluptatem error expedita pariatur\n" +
                            "nihil sint nostrum voluptatem reiciendis et"
                )
            ), fromCache = false
        )
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun CommentErrorNetworkPreview() {
    CommentListScreen(
        homeState = HomeState.Error(AppError.Network)
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun CommentErrorServerPreview() {
    CommentListScreen(
        homeState = HomeState.Error(AppError.Server)
    ) {}
}
