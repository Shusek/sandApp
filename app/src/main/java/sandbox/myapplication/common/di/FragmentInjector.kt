package sandbox.myapplication.common.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sandbox.myapplication.details.RepositoryDetailsFragment
import sandbox.myapplication.search.SearchListFragment


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract fun searchFragment() : SearchListFragment

    @ContributesAndroidInjector()
    abstract fun detailFragment(): RepositoryDetailsFragment
}