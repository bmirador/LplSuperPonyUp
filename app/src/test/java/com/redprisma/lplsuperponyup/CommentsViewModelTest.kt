package com.redprisma.lplsuperponyup

import app.cash.turbine.test
import com.example.domain.Comment
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.util.AppError
import com.redprisma.lplsuperponyup.data.util.DataResult
import com.redprisma.lplsuperponyup.ui.screens.home.CommentListState
import com.redprisma.lplsuperponyup.ui.screens.home.CommentsViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    private val repository: CommentsRepository = mock(CommentsRepository::class.java)
    private lateinit var viewModel: CommentsViewModel

    @Before
    fun setup() {
        viewModel = CommentsViewModel(repository, Dispatchers.Main) // inject test dispatcher
    }

    @Test
    fun `loadComments emits Success state`() = runTest {
        val comments = listOf(Comment(
            id = 1,
            postId = 1,
            name = "test",
            email = "test",
            body = "test",
        ))

        whenever(repository.fetchComments()).thenReturn(
            flowOf(DataResult.Success(comments, fromCache = false, error = null))
        )

        viewModel.loadComments()
        advanceUntilIdle() // now works because we're on test dispatcher

        assertTrue(viewModel.homeState.value is CommentListState.Success)
    }
}

class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
