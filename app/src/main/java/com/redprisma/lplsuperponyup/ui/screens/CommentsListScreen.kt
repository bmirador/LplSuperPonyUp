package com.redprisma.lplsuperponyup.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redprisma.lplsuperponyup.HomeState
import com.redprisma.lplsuperponyup.R
import com.redprisma.lplsuperponyup.data.local.models.Comment
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CommentListScreen(homeState: HomeState, loadComments: () -> Unit) {
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
                        .testTag(stringResource(R.string.loadingindicator_testing)),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

        is HomeState.Success ->
            // Display list of comments when data is successfully loaded
            Column(Modifier.fillMaxSize()) {
                LazyColumn {
                    items(
                        items = homeState.comments,
                        key = { it?.id ?: Uuid.random() }) { comment ->
                        comment?.run {
                            // Display individual comment item
                            RequestedCommentItem(
                                postId = postId,
                                id = id,
                                name = name,
                                email = email,
                                body = body
                            )
                        }
                    }
                }
            }

        is HomeState.Error ->
            // Display error screen with retry option
            ErrorScreen(homeState.errorMessage, loadComments)
    }
}


@Composable
fun RequestedCommentItem(
    modifier: Modifier = Modifier,
    postId: Int,
    id: Int,
    name: String,
    email: String,
    body: String
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = stringResource(R.string.user_icon),
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Top)
        )

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


@Preview
@Composable
private fun CommentLoadingPreview() {
    CommentListScreen(
        HomeState.Loading
    ) { }
}

@Preview
@Composable
private fun CommentSuccessPreview() {
    CommentListScreen(
        HomeState.Success(
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
            )
        )
    ) {}
}

@Preview
@Composable
private fun CommentErrorPreview() {
    CommentListScreen(
        HomeState.Error("Something is wrong!!")
    ) {}
}
