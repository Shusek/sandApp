package sandbox.myapplication.search

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.called
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import sandbox.myapplication.search.repository.PagedSearchRepository
import sandbox.myapplication.search.repository.PagedSearchService

class SearchRepoViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    val pagedSearchRepository = mockk<PagedSearchRepository>()
    val service = PagedSearchService(pagedSearchRepository)
    val viewModel = SearchRepoViewModel(service)
    @Test
    fun shouldSearchReturnProperCountOfLoadedItems() {
         val expectedRepositories = listOf(Repository(1), Repository(2))
        coEvery { pagedSearchRepository.getRepositories(any(), any()) } returns expectedRepositories


        viewModel.search("retrofit")
        viewModel.repositoriesPagedList.observeForever {
            assertEquals(expectedRepositories, it!!)
        }
    }


    @Test
    fun shouldSearchReturnEmptyListWhenQueryIsEmptyWithoutCallRepository() {
        viewModel.search("")

        viewModel.repositoriesPagedList.observeForever {
            assertEquals(0, it!!.size)
        }
        verify {
            pagedSearchRepository wasNot called
        }
    }
}