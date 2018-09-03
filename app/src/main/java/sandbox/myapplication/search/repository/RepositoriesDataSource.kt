package sandbox.myapplication.search.repository

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import kotlinx.coroutines.experimental.runBlocking
import sandbox.myapplication.search.Repository

class RepositoriesDataSource(private val service: PagedSearchRepository,
                             private val query: String)
    : PageKeyedDataSource<Int, Repository>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repository>) {
        try {
            runBlocking {
                val repositories = service.getRepositories(1, query)
                callback.onResult(repositories, 1, 2)
            }
        } catch (e: Exception) {
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        runBlocking {
            val repositories = service.getRepositories(params.key, query)
            callback.onResult(repositories, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        //not required for this use case
    }

    companion object {
        fun getFactory(repositoryPaged: PagedSearchRepository,
                       query: String): Factory<Int, Repository> {
            return object : DataSource.Factory<Int, Repository>() {
                override fun create(): DataSource<Int, Repository> {
                    return RepositoriesDataSource(repositoryPaged, query)
                }
            }
        }
    }
}