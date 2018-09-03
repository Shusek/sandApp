package sandbox.myapplication.search

data class Repository(val id: Long,
                      val userName: String,
                      val repoName: String,
                      val description: String,
                      val stars: Int)