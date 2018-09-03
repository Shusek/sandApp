package sandbox.myapplication.common.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun getRepositories(@Query("page") int: Int, @Query("q") query: String): Deferred<DataRepositoriesApi>

    @GET("repos/{owner}/{repo}")
    fun getRepositoryUserRepository(@Path("owner") userId: Long, @Path("repo") repoName: String): Deferred<RepositoryDetailsApi>


}