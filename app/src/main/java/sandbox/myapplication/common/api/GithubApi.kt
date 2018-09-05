package sandbox.myapplication.common.api

import kotlinx.coroutines.experimental.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun getRepositories(@Query("page") int: Int, @Query("q") query: String): Deferred<DataRepositoriesApi>

    @GET("/repos/{owner}/{repo}/readme")
    @Headers("Accept: application/vnd.github.v3.html")
    fun getRepositoryReadmeFileAsHTML(@Path("owner") userName: String, @Path("repo") repoName: String): Deferred<ResponseBody>

}