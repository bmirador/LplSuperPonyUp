package repository

import com.example.data.CommentsRepository
import com.example.data.util.DataResult
import com.example.data.util.toDomain
import com.example.data.util.toDomainError
import com.example.data.util.toEntity
import com.example.domain.Comment
import com.example.domain.DomainError
import com.redprisma.common.Logger
import com.redprisma.db.local.db.CommentDao
import com.redprisma.network.remote.CommentsService
import com.redprisma.network.remote.toNetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val ERROR_TAG: String = "Error"

class CommentsRepositoryImpl @Inject constructor(
    private val api: CommentsService,
    private val commentDao: CommentDao,
    private val logger: Logger
) : CommentsRepository {

    override fun fetchComments(): Flow<DataResult<List<Comment>>> = flow {
        var loadedFromCache = false
        var domainError: DomainError = DomainError.Unknown()

        try {
            val remoteData = api.getComments().mapNotNull { it?.toEntity() }
            commentDao.insertComments(remoteData)
        } catch (exception: Exception) {
            loadedFromCache = true
            domainError = exception.toNetworkError().toDomainError()
            logger.e(ERROR_TAG, exception.message.toString())
        }

        val data = commentDao.getAllCommentsByPostId(1)
            .map { entities -> entities.map { it.toDomain() } }

        emitAll(
            data.map { comments ->
                if (comments.isEmpty() && loadedFromCache) {
                    DataResult.Error(
                        domainError
                    )
                } else {
                    DataResult.Success(
                        data = comments,
                        error = domainError,
                        fromCache = loadedFromCache
                    )
                }
            }
        )
    }.catch { e ->
        emit(DataResult.Error(DomainError.Unknown()))
    }
}
