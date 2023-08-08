package co.geisyanne.instagram.profile

import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView
import co.geisyanne.instagram.common.model.Post
import co.geisyanne.instagram.common.model.User

interface Profile {

    interface Presenter : BasePresenter {
        fun fetchUserProfile(uuid: String?)
        fun fetchUsersPosts(uuid: String?)
        fun followUser(uuid: String?, follow: Boolean)  // SEGUIR - DEIXAR SEGUIR
        fun clear()
    }

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(user: Pair<User, Boolean?>)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
        fun followUpdated()
    }
}