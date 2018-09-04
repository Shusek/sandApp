package sandbox.myapplication.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.repository_details.*
import sampleapp.istari.myapplication.R
import sandbox.myapplication.common.ToolbarFragment

class RepositoryDetailsFragment : ToolbarFragment() {
    private val repository by lazy { RepositoryDetailsFragmentArgs.fromBundle(arguments).repository }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_details, container, false)
    }

    override fun toolbarId() = R.id.detailToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActionBar().title = repository.repoName
        repoDetailsDescription.text = repository.description
    }
}