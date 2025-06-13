package com.redprisma.lplsuperponyup

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.redprisma.lplsuperponyup.data.util.AppError
import com.redprisma.lplsuperponyup.ui.screens.ErrorScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorScreenTest {
    val context: Context =
        androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun networkError_showsCorrectMessageAndRetryButton() {
        var retried = false
        val expectedText = context.getString(R.string.there_is_a_network_error_we_are_sorry)
        val retryText = context.getString(R.string.retry)

        composeTestRule.setContent {
            ErrorScreen(appError = AppError.Network, loadComments = { retried = true })
        }

        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(retryText)
            .assertIsDisplayed()
            .performClick()

        assert(retried)
    }

    @Test
    fun unauthorizedError_showsCorrectMessage() {
        val expectedText =
            context.getString(R.string.you_are_not_authorized_to_access_this_resource)
        composeTestRule.setContent {
            ErrorScreen(appError = AppError.Unauthorized, loadComments = {})
        }

        composeTestRule
            .onNodeWithText(expectedText)
            .assertIsDisplayed()
    }

    @Test
    fun unknownError_showsGenericMessage() {
        val expectedText =
            context.getString(R.string.we_really_don_t_know_what_is_going_on_here_is_a_generic_message)
        composeTestRule.setContent {
            ErrorScreen(appError = AppError.Unknown(null), loadComments = {})
        }
        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
    }
}
