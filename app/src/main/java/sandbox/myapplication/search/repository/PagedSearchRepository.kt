package sandbox.myapplication.search.repository

import sandbox.myapplication.search.Repository

interface PagedSearchRepository {

    suspend fun getRepositories(page: Int, searchQuery: String): List<Repository>
}