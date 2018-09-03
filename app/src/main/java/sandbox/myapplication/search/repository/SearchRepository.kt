package sandbox.myapplication.search.repository

import sandbox.myapplication.common.api.GithubApi
import sandbox.myapplication.search.Repository
import javax.inject.Inject

class SearchRepository @Inject constructor(private val githubApi: GithubApi) {

    suspend fun getRepositories(page: Int, searchQuery: String): List<Repository> {
        return githubApi.getRepositories(page,searchQuery).await()
                .items
                .map { Repository(it.id) }
    }
}