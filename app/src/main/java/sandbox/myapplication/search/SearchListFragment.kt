package sandbox.myapplication.search

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import javax.inject.Inject

class SearchListFragment: Fragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(SearchRepoViewModel::class.java)
    }

}