package com.redprisma.lplsuperponyup

import com.redprisma.lplsuperponyup.data.domain.Comment
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.util.DataResult
import com.redprisma.lplsuperponyup.ui.screens.home.CommentListState
import com.redprisma.lplsuperponyup.ui.screens.home.CommentsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
import app.cash.turbine.test
import com.redprisma.lplsuperponyup.data.util.AppError

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repo: CommentsRepository = mock()
    private lateinit var viewModel: CommentsViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule(testDispatcher)

    @Before
    fun setUp() {
        viewModel = CommentsViewModel(repo)
    }

    @Test
    fun `emits Loading then Success when repository succeeds`() = runTest {
        // Arrange
        val fakeComments = listOf(
            Comment(
                id = 1, body = "Hi Chopita",
                postId = 1,
                name = "Chopita",
                email = "chopita@gmail.com"
            )
        )
        whenever(repo.fetchComments()).thenReturn(
            flowOf(DataResult.Success(fakeComments, fromCache = false, error = null))
        )

        // Act
        viewModel.loadComments()

        // Assert
        viewModel.homeState.test {
            val flowResult = awaitItem()
            assert(CommentListState.Success(
                comments = fakeComments,
                fromCache = false,
                error = null
            ) == flowResult)
            val success = flowResult as CommentListState.Success
            assert(fakeComments == success.comments)
            assert(!success.fromCache)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Loading then Error when repository fails`() = runTest {
        // Arrange
        val appError = AppError.Network
        whenever(repo.fetchComments()).thenReturn(
            flowOf(DataResult.Error(appError))
        )
        // Act
        viewModel.loadComments()

        // Assert
        viewModel.homeState.test {
            val flowResult = awaitItem()
            assert(CommentListState.Error(appError) == flowResult)
            val error = flowResult as CommentListState.Error
            assert(appError == error.appError)
            cancelAndIgnoreRemainingEvents()
        }
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
