package co.geisyanne.instagram.search.data

import android.os.Handler
import android.os.Looper
import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.Database
import co.geisyanne.instagram.common.model.User

class SearchFakeRemoteDataSource : SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val users = Database.usersAuth.filter {
                it.name.lowercase()
                    .startsWith(name.lowercase()) && it.uuid != Database.sessionAuth!!.uuid  // NÃO BUSCAR O PRÓPRIO USER
            }

            //callback.onSuccess(users.toList())

            callback.onComplete()
        }, 2000)
    }
}