package co.geisyanne.instagram.home.data

import co.geisyanne.instagram.common.base.Cache
import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.Post
import com.google.firebase.auth.FirebaseAuth

class HomeLocalDataSource(
    private val feedCache: Cache<List<Post>>
) : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        val posts = feedCache.get(userUUID)
        if (posts != null) {
            callback.onSuccess(posts)
        } else {
            callback.onFailure("posts não existem")
        }
        callback.onComplete()
    }

    override fun fetchSession(): String {
        return FirebaseAuth.getInstance().uid ?: throw RuntimeException("Usuário não logado!")
        // return Database.sessionAuth ?: throw RuntimeException("Usuário não logado!")
    }

    override fun putFeed(response: List<Post>?) {
        feedCache.put(response)
    }

}