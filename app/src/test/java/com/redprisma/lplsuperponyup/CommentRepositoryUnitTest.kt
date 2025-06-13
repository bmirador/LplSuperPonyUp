package com.redprisma.lplsuperponyup

import com.redprisma.lplsuperponyup.data.remote.CommentsService
import com.redprisma.lplsuperponyup.data.remote.models.CommentDto
import com.redprisma.lplsuperponyup.data.remote.models.toDomain
import com.redprisma.lplsuperponyup.data.repository.CommentsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsRepositoryImplTest {

    private lateinit var service: CommentsService
    private lateinit var repository: CommentsRepositoryImpl

    @Before
    fun setup() {
        service = mock()
        repository = CommentsRepositoryImpl(service)
    }

    @Test
    fun `fetchComments returns success when API returns data`() = runTest {
        val dto = CommentDto(1, 1, "name", "email", "body")
        `when`(service.getComments()).thenReturn(listOf(dto))

        val result = repository.fetchComments().first()

        assertTrue(result.isSuccess)
        assertEquals(listOf(dto.toDomain()), result.getOrNull())
    }

    @Test
    fun `fetchComments returns failure when API returns null`() = runTest {
        `when`(service.getComments()).thenReturn(null)

        val result = repository.fetchComments().first()

        assertTrue(result.isFailure)
        assertEquals("The list is empty!", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchComments returns failure when API returns empty list`() = runTest {
        `when`(service.getComments()).thenReturn(emptyList())

        val result = repository.fetchComments().first()

        assertTrue(result.isFailure)
        assertEquals("The list is empty!", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchComments returns failure when API throws exception`() = runTest {
        `when`(service.getComments()).thenThrow(RuntimeException("Network error"))

        val result = repository.fetchComments().first()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}
