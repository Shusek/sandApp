package sandbox.myapplication.common.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sandbox.myapplication.details.RepositoryDetailsFragment
import sandbox.myapplication.search.di.SearchModule
import sandbox.myapplication.search.view.SearchListFragment


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(SearchModule::class)])
    abstract fun searchFragment() : SearchListFragment

    @ContributesAndroidInjector()
    abstract fun detailFragment(): RepositoryDetailsFragment
}