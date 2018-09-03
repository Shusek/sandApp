package sandbox.myapplication.search.di

import dagger.Module
import dagger.Provides
import sandbox.myapplication.common.api.GithubApi
import sandbox.myapplication.search.repository.GithubPagedSearchRepository
import sandbox.myapplication.search.repository.PagedSearchRepository

@Module
class SearchModule {

    @Provides
    fun providesSearchService(api: GithubApi): PagedSearchRepository {
        return GithubPagedSearchRepository(api)
    }

}