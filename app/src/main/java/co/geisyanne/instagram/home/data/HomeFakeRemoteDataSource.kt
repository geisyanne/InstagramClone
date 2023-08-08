package co.geisyanne.instagram.home.data

import android.os.Handler
import android.os.Looper
import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.Database
import co.geisyanne.instagram.common.model.Post

class HomeFakeRemoteDataSource : HomeDataSource {

    override fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val feed = Database.feeds[userUUID]

            callback.onSuccess(feed?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }

}