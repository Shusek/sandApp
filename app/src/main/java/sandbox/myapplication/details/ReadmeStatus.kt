package sandbox.myapplication.details

sealed class ReadmeStatus
object ReadmeStatusLoading : ReadmeStatus()
object ReadmeStatusNotFound : ReadmeStatus()
data class ReadmeStatusError(val retry: () -> Unit) : ReadmeStatus()

data class ReadmeStatusSuccess(val readmeHTML: String) : ReadmeStatus()

