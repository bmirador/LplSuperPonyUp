// CommentListScreenTest.kt
package com.redprisma.lplsuperponyup

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.redprisma.lplsuperponyup.data.local.models.Comment
import com.redprisma.lplsuperponyup.ui.screens.CommentListScreen
import org.junit.Rule
import org.junit.Test


class CommentListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysInitialStateMessage() {
        composeTestRule.setContent {
            CommentListScreen(homeState = HomeState.Initial, loadComments = {})
        }

        composeTestRule
            .onNodeWithText("If you are reading this something, somewhere, went really wrong")
            .assertIsDisplayed()
    }

    @Test
    fun showsLoadingIndicator() {
        composeTestRule.setContent {
            CommentListScreen(homeState = HomeState.Loading, loadComments = {})
        }

        composeTestRule
            .onNode(hasTestTag("loadingIndicator")).assertIsDisplayed()
            .assertIsDisplayed()
    }

    @Test
    fun displaysCommentsInSuccessState() {
        val comments = listOf(
            Comment(1, 1, "John Doe", "john@example.com", "This is a comment."),
            Comment(1, 2, "Jane Smith", "jane@example.com", "Another comment.")
        )

        composeTestRule.setContent {
            CommentListScreen(homeState = HomeState.Success(comments), loadComments = {})
        }

        // Check both names are shown
        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jane Smith").assertIsDisplayed()

        // Check emails
        composeTestRule.onNodeWithText("john@example.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("jane@example.com").assertIsDisplayed()

        // Check IDs
        composeTestRule.onNodeWithText("id: 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("id: 2").assertIsDisplayed()
    }

    @Test
    fun displaysErrorMessage() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val errorMsg = "Something is wrong!!"

        composeTestRule.setContent {
            CommentListScreen(homeState = HomeState.Error(errorMsg), loadComments = {})
        }

        composeTestRule.onNodeWithText(context.getString(R.string.you_got_us_error, errorMsg))
            .assertIsDisplayed()
    }
}
