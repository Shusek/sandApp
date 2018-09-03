package sandbox.myapplication.common.api

import kotlinx.serialization.Serializable

@Serializable
data class DataRepositoriesApi(val items: List<RepositoryApi>)

@Serializable
data class RepositoryApi(val id: Long,
                         val name: String,
                         val owner: OwnerApi,
                         val stargazers_count: Int,
                         val description: String?)

@Serializable
data class OwnerApi(val login: String)