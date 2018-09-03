package sandbox.myapplication.common.api

import kotlinx.serialization.Serializable

@Serializable
data class DataRepositoriesApi(val items:List<RepositoryApi>)

@Serializable
data class RepositoryApi(val id:Long, val name:String, val description:String)