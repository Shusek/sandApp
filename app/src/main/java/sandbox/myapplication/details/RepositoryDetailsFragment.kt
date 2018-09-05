package sandbox.myapplication.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.repository_details.*
import sampleapp.istari.myapplication.R
import sandbox.myapplication.common.ToolbarFragment
import sandbox.myapplication.common.viewmodel.ViewModelFactory
import sandbox.myapplication.details.viewmodel.RepositoryDetailsViewModel
import javax.inject.Inject

class RepositoryDetailsFragment : ToolbarFragment() {
    @Inject
    lateinit var factory: ViewModelFactory<RepositoryDetailsViewModel>
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(RepositoryDetailsViewModel::class.java)
    }

    private val repository by lazy { RepositoryDetailsFragmentArgs.fromBundle(arguments).repository }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_details, container, false)
    }

    override fun toolbarId() = R.id.detailToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActionBar().title = repository.repoName
        repoDetailsDescription.text = repository.description
        viewModel.readmeStatus.observe(this, Observer {
            loadData(it!!)
        })

        viewModel.loadDetailsFromRepository(repository)
    }

    private fun loadData(readmeStatus: ReadmeStatus) {
        when (readmeStatus) {
            is ReadmeStatusSuccess -> {
                loadingBar.hide()
                errorView.hide()
                repositoryReadme.show()
                repositoryReadme.loadData(readmeStatus.readmeHTML, "text/html", "UTF-8")
            }
            is ReadmeStatusError -> {
                loadingBar.hide()
                repositoryReadme.hide()
                errorView.show()
                errorView.setText(R.string.readme_error_network_issue)
                errorView.setOnClickListener { readmeStatus.retry() }
            }
            ReadmeStatusLoading -> {
                loadingBar.show()
                repositoryReadme.hide()
                errorView.hide()
            }
            ReadmeStatusNotFound -> {
                loadingBar.hide()
                repositoryReadme.hide()
                errorView.show()
                errorView.setText(R.string.readme_error_not_found)
                errorView.setOnClickListener(null)
            }
        }
    }

    fun View.hide() {
        visibility = GONE
    }

    fun View.show() {
        visibility = VISIBLE
    }
}