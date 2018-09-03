package sandbox.myapplication.search.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import sandbox.myapplication.common.api.DataRepositoriesApi
import sandbox.myapplication.common.api.GithubApi
import sandbox.myapplication.common.api.RepositoryApi
import sandbox.myapplication.search.Repository

class GithubPagedSearchRepositoryTest {
    private val api: GithubApi = mock()
    private val searRepository = GithubPagedSearchRepository(api)

    @Test
    fun shouldRepositoryCallApiWithPageAndQuery() = runBlocking {
        mockReturnRepositories(14, "someQuery", emptyList())
        searRepository.getRepositories(14, "someQuery")

        verify(api).getRepositories(14, "someQuery")
        verifyNoMoreInteractions(api)
    }

    @Test
    fun shouldRepositoryProperParsed() = runBlocking {

        mockReturnRepositories(1, "query", listOf(RepositoryApi(4, "someName", "Description"),
                RepositoryApi(2, "otherName", "Other Description")))

        val queriedRepositories = searRepository.getRepositories(1, "query")
        val expectedRepositories = listOf(Repository(4), Repository(2))
        assertEquals(expectedRepositories, queriedRepositories)
    }

    private fun mockReturnRepositories(page: Int, query: String, items: List<RepositoryApi>) {
        whenever(api.getRepositories(page, query))
                .thenReturn(async {
                    DataRepositoriesApi(items)
                })
    }

}