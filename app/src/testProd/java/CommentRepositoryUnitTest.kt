import app.cash.turbine.test
import com.redprisma.lplsuperponyup.data.local.db.CommentDao
import com.redprisma.lplsuperponyup.data.models.Comment
import com.redprisma.lplsuperponyup.data.remote.CommentsService
import com.redprisma.lplsuperponyup.data.remote.models.CommentDto
import com.redprisma.lplsuperponyup.data.remote.models.toEntity
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.repository.CommentsRepositoryImpl
import com.redprisma.lplsuperponyup.data.util.AppError
import com.redprisma.lplsuperponyup.data.util.DataResult
import com.redprisma.lplsuperponyup.logger.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsRepositoryImplTest {

    private val api: CommentsService = mock()
    private val dao: CommentDao = mock()
    private val logger: Logger = mock()
    private lateinit var repository: CommentsRepository

    private val fakeDto = CommentDto(1, 1, "John Doe", "john@example.com", "Hello world")
    private val fakeEntity = fakeDto.toEntity()
    private val fakeDomain = Comment(1, 1, "John Doe", "john@example.com", "Hello world")

    @Before
    fun setup() {
        repository = CommentsRepositoryImpl(api, dao, logger)
    }

    @Test
    fun `fetchComments - network success, data returned`() = runTest {
        whenever(api.getComments()).thenReturn(listOf(fakeDto))
        whenever(dao.getAllCommentsByPostId(1)).thenReturn(flowOf(listOf(fakeEntity)))

        repository.fetchComments().test {
            val result = awaitItem()
            assert(result is DataResult.Success)
            val success = result as DataResult.Success
            assert(success.data == listOf(fakeDomain))
            assert(!success.fromCache)
            assert(success.error == null)
            awaitComplete()
        }
    }

    @Test
    fun `fetchComments - network fails, fallback to empty cache`() = runTest {
        whenever(api.getComments()).thenThrow(RuntimeException("No internet"))
        whenever(dao.getAllCommentsByPostId(1)).thenReturn(flowOf(emptyList()))

        repository.fetchComments().test {
            val result = awaitItem()
            assert(result is DataResult.Error)
            val error = result as DataResult.Error
            assert(error.appError is AppError.Network || error.appError is AppError.Unknown)
            awaitComplete()
        }
    }

    @Test
    fun `fetchComments - network fails, fallback to non-empty cache`() = runTest {
        whenever(api.getComments()).thenThrow(RuntimeException("No internet"))
        whenever(dao.getAllCommentsByPostId(1)).thenReturn(flowOf(listOf(fakeEntity)))

        repository.fetchComments().test {
            val result = awaitItem()
            assert(result is DataResult.Success)
            val success = result as DataResult.Success
            assert(success.data == listOf(fakeDomain))
            assert(success.fromCache)
            assert(success.error is AppError)
            awaitComplete()
        }
    }
}
