package com.redprisma.lplsuperponyup.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.redprisma.lplsuperponyup.R
import com.redprisma.lplsuperponyup.data.domain.Comment
import com.redprisma.lplsuperponyup.data.util.AppError
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CommentListScreen(
    modifier: Modifier = Modifier,
    homeState: CommentListState,
    loadComments: () -> Unit
) {
    // Render different UI based on the current state
    when (homeState) {
        is CommentListState.Initial ->
            // Shown if something unexpected happened before loading
            Text(stringResource(R.string.if_you_are_reading_this_something_somewhere_went_really_wrong))

        is CommentListState.Loading ->
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

        is CommentListState.Success ->
            // Display list of comments when data is successfully loaded
            Column(
                modifier,
                verticalArrangement = if (homeState.comments.isEmpty()) Center else Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (homeState.comments.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_comments_were_found)
                    )
                } else {
                    if (homeState.fromCache) {
                        Text(stringResource(R.string.showing_results_from_cache_there_was_a_problem_retrieving_the_latest_comments))
                    }
                    LazyColumn {
                        itemsIndexed(
                            homeState.comments,
                            key = { _, comment -> comment.id }) { i, comment ->
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                comment.run {
                                    CommentItem(
                                        id = id,
                                        name = name,
                                        email = email,
                                        body = body
                                    )
                                }

                                if (i < homeState.comments.lastIndex) {
                                    HorizontalDivider(modifier, 1.dp, Color.LightGray)
                                }
                            }
                        }
                    }
                }
            }

        is CommentListState.Error ->
            // Display error screen with retry option
            ErrorScreen(homeState.appError, loadComments)
    }
}


@Preview(showBackground = true)
@Composable
private fun CommentLoadingPreview() {
    CommentListScreen(
        homeState = CommentListState.Loading,
    ) { }
}

@Preview(showBackground = true)
@Composable
private fun CommentSuccessPreview() {
    CommentListScreen(
        homeState = CommentListState.Success(
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
            ),
            fromCache = false,
            error = AppError.Unknown()
        )
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun CommentErrorNetworkPreview() {
    CommentListScreen(
        homeState = CommentListState.Error(AppError.Network)
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun CommentErrorServerPreview() {
    CommentListScreen(
        homeState = CommentListState.Error(AppError.Server)
    ) {}
}
