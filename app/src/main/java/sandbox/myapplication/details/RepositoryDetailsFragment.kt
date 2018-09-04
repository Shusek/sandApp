package sandbox.myapplication.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sampleapp.istari.myapplication.R

class RepositoryDetailsFragment : Fragment() {
    private val repository by lazy { RepositoryDetailsFragmentArgs.fromBundle(arguments).repository }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_details, container, false)
    }
}