package co.geisyanne.instagram.post

import android.net.Uri
import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView
import co.geisyanne.instagram.common.model.Picture

interface Post {

    interface Presenter : BasePresenter {
        fun selectUri(uri: Uri)
        fun getSelectedUri(): Uri?
        fun fetchPictures()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayFullPictures(posts: List<Picture>)
        fun displayEmptyPictures()
        fun displayRequestFailure(message: String)
    }
}