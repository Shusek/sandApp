package sandbox.myapplication.search.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import sandbox.myapplication.search.Repository
import javax.inject.Inject

class PagedSearchService @Inject constructor(private val pagedSearchRepository: PagedSearchRepository) {
    fun search(queryFilter: String): LiveData<PagedList<Repository>> {
        return LivePagedListBuilder(
                RepositoriesDataSource.getFactory(pagedSearchRepository, queryFilter),
              MAXIMUM_REPOS_PER_REQUEST).build()
    }

    companion object {
        private const val MAXIMUM_REPOS_PER_REQUEST = 1
    }


}