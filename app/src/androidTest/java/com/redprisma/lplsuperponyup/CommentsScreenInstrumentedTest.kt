package com.redprisma.lplsuperponyup

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.redprisma.lplsuperponyup.data.models.Comment
import com.redprisma.lplsuperponyup.ui.screens.CommentListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommentListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            CommentListScreen(homeState = HomeState.Loading, loadComments = {})
        }

        composeTestRule
            .onNodeWithTag("loadingIndicator")
            .assertIsDisplayed()
    }

    @Test
    fun successState_showsListOfComments() {
        val comments = listOf(
            Comment(1, 1, "John", "john@example.com", "Hello"),
            Comment(1, 2, "Jane", "jane@example.com", "Hi there")
        )

        composeTestRule.setContent {
            CommentListScreen(
                homeState = HomeState.Success(comments, fromCache = false),
                loadComments = {}
            )
        }

        composeTestRule
            .onAllNodesWithText("Hello")
            .assertCountEquals(1)

        composeTestRule
            .onAllNodesWithText("Hi there")
            .assertCountEquals(1)
    }

    @Test
    fun emptySuccessState_showsEmptyMessage() {
        composeTestRule.setContent {
            CommentListScreen(
                homeState = HomeState.Success(emptyList(), fromCache = false),
                loadComments = {}
            )
        }

        composeTestRule
            .onNodeWithText("No comments were found")
            .assertIsDisplayed()
    }

    @Test
    fun successFromCache_showsWarningBanner() {
        val comments = listOf(
            Comment(1, 1, "John", "john@example.com", "Hello")
        )

        composeTestRule.setContent {
            CommentListScreen(
                homeState = HomeState.Success(comments, fromCache = true),
                loadComments = {}
            )
        }

        composeTestRule
            .onNodeWithText("Showing results from cache, there was a problem retrieving the latest comments")
            .assertIsDisplayed()
    }
}
