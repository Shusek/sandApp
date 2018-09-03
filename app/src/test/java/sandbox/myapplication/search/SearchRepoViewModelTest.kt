package sandbox.myapplication.search

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import sandbox.myapplication.search.repository.PagedSearchRepository
import sandbox.myapplication.search.repository.PagedSearchService

class SearchRepoViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Test
    fun shouldSearchReturnProperCountOfLoadedItems() {
        val pagedSearchRepository = mockk<PagedSearchRepository>()
        val expectedRepositories = listOf(Repository(1), Repository(2))
        coEvery { pagedSearchRepository.getRepositories(any(), any()) } returns expectedRepositories
        val service = PagedSearchService(pagedSearchRepository)
        val viewModel = SearchRepoViewModel(service)

        viewModel.search("retrofit")
        viewModel.repositoriesPagedList.observeForever {
            assertEquals(expectedRepositories, it!!)
        }
    }
}