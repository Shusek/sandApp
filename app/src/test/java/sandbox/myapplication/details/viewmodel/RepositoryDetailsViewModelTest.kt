package sandbox.myapplication.details.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import io.mockk.*
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.async
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import sandbox.myapplication.common.RepositoryFactory.Companion.createRepository
import sandbox.myapplication.common.api.GithubApi
import sandbox.myapplication.details.*
import sandbox.myapplication.details.repository.ReadmeService

class RepositoryDetailsViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    private val api = mockk<GithubApi>()
    private val service = ReadmeService(api)
    private val viewmodel: RepositoryDetailsViewModel = RepositoryDetailsViewModel(service)

    @Test
    fun shouldReturnSuccessReadmeStatusWhenApiReturnData() {
        val repository = createRepository(usrName = "user", repoName = "repo")

        every { api.getRepositoryReadmeFileAsHTML(any(), any()) } returns
                async(Unconfined)
                { ResponseBody.create(MediaType.parse("application/html"), "htmlText") }

        val observer = mockObserverReadmeStatus()
        viewmodel.readmeStatus.observeForever(observer)

        viewmodel.loadDetailsFromRepository(repository)

        verifySequence {
            observer.onChanged(ReadmeStatusLoading)
            observer.onChanged(ReadmeStatusSuccess("htmlText"))
        }
    }

    @Test
    fun shouldReturnNotFoundStatusWhenApiReturn404Error() {
        val repository = createRepository(usrName = "user", repoName = "repo")

        every { api.getRepositoryReadmeFileAsHTML(any(), any()) } throws HttpException(createErrorResponse(404))
        val observer = mockObserverReadmeStatus()

        viewmodel.readmeStatus.observeForever(observer)

        viewmodel.loadDetailsFromRepository(repository)

        verifySequence {
            observer.onChanged(ReadmeStatusLoading)
            observer.onChanged(ReadmeStatusNotFound)
        }
    }


    @Test
    fun shouldReturnErrorStatusWhenApiReturn500Error() {
        val repository = createRepository(usrName = "user", repoName = "repo")

        every { api.getRepositoryReadmeFileAsHTML(any(), any()) } throws HttpException(createErrorResponse(500))
        val observer = mockObserverReadmeStatus()

        viewmodel.readmeStatus.observeForever(observer)

        viewmodel.loadDetailsFromRepository(repository)

        verifySequence {
            observer.onChanged(ReadmeStatusLoading)
            observer.onChanged(ofType<ReadmeStatusError>())
        }
    }


    @Test
    fun shouldRetryAfterFailedCallWork() {
        val repository = createRepository(usrName = "user", repoName = "repo")

        every { api.getRepositoryReadmeFileAsHTML(any(), any()) } throws HttpException(createErrorResponse(500))
        val observer = mockObserverReadmeStatus()

        viewmodel.readmeStatus.observeForever(observer)

        viewmodel.loadDetailsFromRepository(repository)

        val slot = slot<ReadmeStatusError>()
        verify {
            observer.onChanged(ReadmeStatusLoading)
            observer.onChanged(capture(slot))
        }
        every { api.getRepositoryReadmeFileAsHTML(any(), any()) } returns async(Unconfined)
        { ResponseBody.create(MediaType.parse("application/html"), "SomeHtmlFile") }

        clearMocks(observer)
        slot.captured.retry()
        verify {
            observer.onChanged(ReadmeStatusLoading)
            observer.onChanged(ReadmeStatusSuccess("SomeHtmlFile"))
        }
    }

    private fun mockObserverReadmeStatus(): Observer<ReadmeStatus> =
            mockk(relaxed = true)

    private fun createErrorResponse(errorCode: Int, text: String = ""): Response<Any> =
            Response.error(
                    errorCode,
                    ResponseBody.create(MediaType.parse("application/json"), text)
            )


}