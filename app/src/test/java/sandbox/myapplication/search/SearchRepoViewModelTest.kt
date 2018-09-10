package sandbox.myapplication.search

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import io.mockk.called
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import sandbox.myapplication.common.RepositoryFactory
import sandbox.myapplication.search.repository.PagedSearchRepository
import sandbox.myapplication.search.repository.PagedSearchService

class SearchRepoViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    private val pagedSearchRepository = mockk<PagedSearchRepository>()
    private val service = PagedSearchService(pagedSearchRepository)
    private val viewModel = SearchRepoViewModel(service)

    @Test
    fun shouldSearchReturnProperCountOfLoadedItems() {
        val expectedRepositories = listOf(
                RepositoryFactory.createRepository(1),
                RepositoryFactory.createRepository(2))
        coEvery { pagedSearchRepository.getRepositories(any(), any()) } returns expectedRepositories

        val singleObserver = viewModel.repositoriesPagedList.observeSingle()

        viewModel.search("retrofit")

        assertEquals(expectedRepositories, singleObserver.value)
    }

    @Test
    fun shouldSearchReturnEmptyListWhenQueryIsEmptyWithoutCallRepository() {
        val singleObserver = viewModel.repositoriesPagedList.observeSingle()
        viewModel.search("")

        assertEquals(emptyList<PagedList<Repository>>(), singleObserver.value)
        verify {
            pagedSearchRepository wasNot called
        }
    }

    @Test
    fun shouldLoadDataFromMultiplePages() {
        val firstPage = (1 until 100).map { RepositoryFactory.createRepository(it.toLong()) }
        val secondPage = (100..150).map { RepositoryFactory.createRepository(it.toLong()) }

        coEvery { pagedSearchRepository.getRepositories(eq(1), any()) } returns firstPage
        coEvery { pagedSearchRepository.getRepositories(eq(2), any()) } returns secondPage

        val singleObserver = viewModel.repositoriesPagedList.observeSingle()
        viewModel.search("abc")

        val expectedAllItems = firstPage + secondPage

        val pagedList = singleObserver.value!!
        assertEquals(firstPage, pagedList)
        //load second page
        pagedList.loadAround(45)
        assertEquals(expectedAllItems, singleObserver.value)
    }

    private fun LiveData<PagedList<Repository>>.observeSingle() =
            SingleObserver<PagedList<Repository>>()
                    .apply { observeForever(this) }


    private class SingleObserver<T> : Observer<T> {
        var value: T? = null
        override fun onChanged(newValue: T?) {
            if (value != null) {
                fail("This observer can contain only one value, old value = $value, new value = $newValue ")
            }
            this.value = newValue
        }
    }
}