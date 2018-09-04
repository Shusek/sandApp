package sandbox.myapplication.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sampleapp.istari.myapplication.R

class RepositoryDetailsFragment : Fragment() {
    private val repository by lazy { RepositoryDetailsFragmentArgs.fromBundle(arguments).repository }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getSupportActionBar().apply {
            title = repository.repoName
            subtitle = repository.description
        }
    }

    private fun getSupportActionBar() = (requireActivity() as AppCompatActivity).supportActionBar!!
}