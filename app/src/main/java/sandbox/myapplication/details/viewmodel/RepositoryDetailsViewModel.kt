package sandbox.myapplication.details.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import retrofit2.HttpException
import sandbox.myapplication.details.*
import sandbox.myapplication.details.repository.ReadmeService
import sandbox.myapplication.search.Repository
import javax.inject.Inject


class RepositoryDetailsViewModel @Inject constructor(private val readmeService: ReadmeService) : ViewModel() {
    val readmeStatus = MutableLiveData<ReadmeStatus>()
    var job: Job? = null

    fun loadDetailsFromRepository(repository: Repository) {
        job?.cancel()
        job = launch(Unconfined) {
            readmeStatus.value = ReadmeStatusLoading
            //api call is called on other thread
            val fetchStatus = loadReadmeFile(repository)
            readmeStatus.postValue(fetchStatus)
        }
    }

    private suspend fun loadReadmeFile(repository: Repository) =
        try {
            val data = readmeService.getReadmeFileAsHtml(repository.userName, repository.repoName)
            ReadmeStatusSuccess(data)
        } catch (e: Exception) {
            when {
                (e is HttpException && e.code() == 404) -> ReadmeStatusNotFound
                else -> ReadmeStatusError { loadDetailsFromRepository(repository) }
            }
    }

    override fun onCleared() {
        job?.cancel()
    }
}
