package sandbox.myapplication.search.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.*
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.repository_list.*
import sampleapp.istari.myapplication.R
import sandbox.myapplication.common.ToolbarFragment
import sandbox.myapplication.common.viewmodel.ViewModelFactory
import sandbox.myapplication.search.SearchRepoViewModel
import javax.inject.Inject


class SearchListFragment : ToolbarFragment() {
    @Inject
    lateinit var factory: ViewModelFactory<SearchRepoViewModel>
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(SearchRepoViewModel::class.java)
    }
    private val listAdapter by lazy {
        RepositoriesAdapter {
            val repositoryDetailAction = SearchListFragmentDirections.repositoryDetailAction(it)
            navigationController.navigate(repositoryDetailAction, null)
        }
    }

    private val navigationController by lazy {
        Navigation.findNavController(view!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActionBar().setDisplayShowTitleEnabled(false)
        getActionBar().setDisplayShowHomeEnabled(false)
        setHasOptionsMenu(true)
        repositoryList.adapter = listAdapter
        viewModel.repositoriesPagedList.observe(this, Observer {
            listAdapter.submitList(it)
        })
    }

    override fun toolbarId() = R.id.searchToolbar

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val findItem = menu.findItem(R.id.action_search)
        val searchItem = findItem.actionView as SearchView
        searchItem.setIconifiedByDefault(false)
        searchItem.maxWidth = Integer.MAX_VALUE

        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.search(newText)
                return true
            }

        })

    }
}