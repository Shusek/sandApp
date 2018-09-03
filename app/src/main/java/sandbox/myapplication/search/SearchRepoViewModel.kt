package sandbox.myapplication.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import sandbox.myapplication.search.repository.PagedSearchService
import javax.inject.Inject

class SearchRepoViewModel @Inject constructor(private val searchService: PagedSearchService) : ViewModel() {
    private val query = MutableLiveData<String>()
    val repositoriesPagedList: LiveData<PagedList<Repository>> = Transformations.switchMap(query) {
        searchService.search(it)
    }

    fun search(queryFilter: String) {
        query.value = queryFilter
    }

}