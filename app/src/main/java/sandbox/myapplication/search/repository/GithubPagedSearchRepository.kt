package sandbox.myapplication.search.repository

import sandbox.myapplication.common.api.GithubApi
import sandbox.myapplication.search.Repository

class GithubPagedSearchRepository(private val githubApi: GithubApi) : PagedSearchRepository {

    override suspend fun getRepositories(page: Int, searchQuery: String): List<Repository> {
        return githubApi.getRepositories(page, searchQuery).await()
                .items
                .map { Repository(it.id) }
    }
}