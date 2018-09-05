package sandbox.myapplication.search.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import sandbox.myapplication.common.api.DataRepositoriesApi
import sandbox.myapplication.common.api.GithubApi
import sandbox.myapplication.common.api.OwnerApi
import sandbox.myapplication.common.api.RepositoryApi
import sandbox.myapplication.search.Repository

class GithubPagedSearchRepositoryTest {
    private val api: GithubApi = mockk()
    private val searRepository = GithubPagedSearchRepository(api)

    @Test
    fun shouldRepositoryCallApiWithPageAndQuery() = runBlocking {
        mockReturnRepositories(14, "someQuery", emptyList())
        searRepository.getRepositories(14, "someQuery")

        verifyAll { api.getRepositories(14, "someQuery") }
    }

    @Test
    fun shouldRepositoryProperParsed() = runBlocking {

        val firstRepoApi = RepositoryApi(1, "FirstRepoName", OwnerApi("owner"), 545, "Some description")
        val secondRepoApi = RepositoryApi(4, "SecondRepoName", OwnerApi("owner2"), 15, "Other description")

        mockReturnRepositories(1, "query", listOf(
                firstRepoApi,
                secondRepoApi))

        val queriedRepositories = searRepository.getRepositories(1, "query")
        val expectedRepositories = listOf(
                Repository(firstRepoApi.id, firstRepoApi.owner.login, firstRepoApi.name, firstRepoApi.description!!, firstRepoApi.stargazers_count),
                Repository(secondRepoApi.id, secondRepoApi.owner.login, secondRepoApi.name, secondRepoApi.description!!, secondRepoApi.stargazers_count)
        )
        assertEquals(expectedRepositories, queriedRepositories)
    }

    private fun mockReturnRepositories(page: Int, query: String, items: List<RepositoryApi>) {
        every { api.getRepositories(page, query) } returns async(Unconfined) {
            DataRepositoriesApi(items)
        }
    }

}