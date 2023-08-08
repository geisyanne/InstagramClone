package co.geisyanne.instagram.add.data

import android.net.Uri
import co.geisyanne.instagram.common.base.RequestCallback

interface AddDataSource {

    fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {
        throw UnsupportedOperationException()
    }  // PARA NÃO SER OBG IMPLEMENTAR

    fun fetchSession(): String {
        throw UnsupportedOperationException()
    }


}