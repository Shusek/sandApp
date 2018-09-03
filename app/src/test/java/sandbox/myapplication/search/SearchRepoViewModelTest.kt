package sandbox.myapplication.search

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import sandbox.myapplication.search.repository.PagedSearchRepository
import sandbox.myapplication.search.repository.PagedSearchService

class SearchRepoViewModelTest {
    val repositoryPaged: PagedSearchRepository = mock()
    val service = PagedSearchService(repositoryPaged)
    val viewModel = SearchRepoViewModel(service)
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Test
    fun shouldSearch() = runBlocking {
        whenever(repositoryPaged.getRepositories(1, "retrofit")).thenReturn(emptyList())
        viewModel.search("retrofit")
        viewModel.repositoriesPagedList.observeForever {
            assertEquals(0, it!!.size)
        }
    }
}