package co.geisyanne.instagram.add.data

import android.net.Uri
import co.geisyanne.instagram.common.base.RequestCallback

class AddRepository(
    private val remoteDataSource: FireAddDataSource,
    private val localDataSource: AddLocalDataSource
) {

    fun createPost(
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {

        val uuid = localDataSource.fetchSession()

        remoteDataSource.createPost(uuid, uri, caption, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }
}