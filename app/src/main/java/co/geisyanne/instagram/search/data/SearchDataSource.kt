package co.geisyanne.instagram.search.data

import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.User

interface SearchDataSource {

    fun fetchUsers(name: String, callback: RequestCallback<List<User>>)

}