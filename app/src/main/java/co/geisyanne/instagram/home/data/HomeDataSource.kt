package co.geisyanne.instagram.home.data

import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.Post

interface HomeDataSource {

    fun logout() {throw UnsupportedOperationException() }

    fun fetchFeed(userUUID: String, callback: RequestCallback<List<Post>>)

    fun fetchSession() : String  {throw UnsupportedOperationException()} // BUSCAR SESSÂO, PARA GARANTIR Q O USER ESTEJA LOGADO

    fun putFeed(response: List<Post>?) {throw UnsupportedOperationException()} // COLOCAR NO CACHE

}
