package sandbox.myapplication.search.view

import android.arch.paging.PagedListAdapter
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import sampleapp.istari.myapplication.R
import sandbox.myapplication.search.Repository

class RepositoriesAdapter(private val onClick: (Repository) -> Unit) :
        PagedListAdapter<Repository, RepositoriesAdapter.RepositoryViewHolder>(RepositoriesCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder{
        return RepositoryViewHolder(parent.inflate(R.layout.repository_list_item))
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position)!!, onClick)
    }

    class RepositoryViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Repository, onClick: (Repository) -> Unit) {
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}