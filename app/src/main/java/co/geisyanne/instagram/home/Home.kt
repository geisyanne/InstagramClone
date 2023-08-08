package co.geisyanne.instagram.home

import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView
import co.geisyanne.instagram.common.model.Post


interface Home {

    interface Presenter : BasePresenter {
        fun fetchFeed()
        fun logout()
        fun clear() // LIMPAR CACHE
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
    }

}