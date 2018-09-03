package sandbox.myapplication.search.view

import android.support.v7.util.DiffUtil
import sandbox.myapplication.search.Repository

object RepositoriesCallback : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository?, newItem: Repository?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Repository?, newItem: Repository?): Boolean {
        return oldItem == newItem
    }
}