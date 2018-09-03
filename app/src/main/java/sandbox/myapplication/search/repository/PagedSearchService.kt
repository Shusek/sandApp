package sandbox.myapplication.search.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import sandbox.myapplication.search.Repository

class PagedSearchService(private val searchRepository: SearchRepository) {
    fun search(queryFilter: String): LiveData<PagedList<Repository>> {
        return LivePagedListBuilder(
                RepositoriesDataSource.getFactory(searchRepository, queryFilter),
              MAXIMUM_REPOS_PER_REQUEST).build()
    }

    companion object {
        private const val MAXIMUM_REPOS_PER_REQUEST = 100
    }


}