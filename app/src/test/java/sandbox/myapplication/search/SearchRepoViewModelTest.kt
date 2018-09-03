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

    @Test
    fun shouldLoadDataFromMultiplePages() {
        val firstPage = (1..100).map { Repository(it.toLong()) }
        val secondPage = (100..150).map { Repository(it.toLong()) }

        coEvery { pagedSearchRepository.getRepositories(eq(1), any()) } returns firstPage
        coEvery { pagedSearchRepository.getRepositories(eq(2), any()) } returns secondPage

        val liveData = service.search("abc")
        liveData.observeForever {
            val expectedAllItems = firstPage + secondPage
            it!!.loadAround(expectedAllItems.size - 1)
            assertEquals(expectedAllItems, it)
        }

    }
}