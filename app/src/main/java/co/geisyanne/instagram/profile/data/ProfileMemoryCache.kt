package co.geisyanne.instagram.profile.data

import co.geisyanne.instagram.common.base.Cache
import co.geisyanne.instagram.common.model.User

object ProfileMemoryCache : Cache<Pair<User, Boolean?>> {

    private var userAuth: Pair<User, Boolean?>? = null

    override fun isCached(): Boolean {
        return userAuth != null
    }

    override fun get(key: String): Pair<User, Boolean?>? {
        if (userAuth?.first?.uuid == key) {
            return userAuth
        }
        return null
    }

    override fun put(data: Pair<User, Boolean?>?) {
        userAuth = data
    }
}