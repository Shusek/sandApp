package sandbox.myapplication.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(val id: Long,
                      val userName: String,
                      val repoName: String,
                      val description: String,
                      val stars: Int) : Parcelable