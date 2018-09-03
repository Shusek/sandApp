package sandbox.myapplication.search.view

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.repository_list.*
import sampleapp.istari.myapplication.R


class SearchListFragment : DaggerFragment() {

    private val listAdapter by lazy { RepositoriesAdapter {} }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repositoryList.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search).actionView as SearchView
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

        })

    }
}