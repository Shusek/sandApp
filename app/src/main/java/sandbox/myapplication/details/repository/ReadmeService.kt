package sandbox.myapplication.details.repository

import retrofit2.HttpException
import sandbox.myapplication.common.api.GithubApi
import sandbox.myapplication.details.ReadmeStatus
import sandbox.myapplication.details.ReadmeStatusNotFound
import sandbox.myapplication.details.ReadmeStatusSuccess
import javax.inject.Inject

class ReadmeService @Inject constructor(private val githubApi: GithubApi) {
    suspend fun getReadmeFileAsHtml(userName: String, repositoryName: String): String {
        return githubApi.getRepositoryReadmeFileAsHTML(userName, repositoryName).await().string()
    }

}