package sandbox.myapplication.common

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import dagger.android.support.DaggerFragment


abstract class ToolbarFragment : DaggerFragment() {

    abstract fun toolbarId(): Int
    protected fun getActionBar(): ActionBar = getToolbarActivity().supportActionBar!!

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val findViewById = view.findViewById<Toolbar>(toolbarId())
        val toolbarActivity = getToolbarActivity()
        toolbarActivity.setSupportActionBar(findViewById)
        if (toolbarActivity is NavigationActionBar) {
            toolbarActivity.setupActionBarWithNavController()
        }
    }

    private fun getToolbarActivity() = requireActivity() as AppCompatActivity

}

