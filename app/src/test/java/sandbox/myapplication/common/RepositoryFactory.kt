package sandbox.myapplication.common

import sandbox.myapplication.search.Repository

class RepositoryFactory private constructor() {
    companion object {
        fun createRepository(id: Long = 4, usrName: String = "usrName", repoName: String = "repoName"): Repository {
            return Repository(id, usrName, repoName, "Description", 5)
        }
    }
}