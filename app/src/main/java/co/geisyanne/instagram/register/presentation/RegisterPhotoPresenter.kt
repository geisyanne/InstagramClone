package co.geisyanne.instagram.register.presentation

import android.net.Uri
import co.geisyanne.instagram.register.RegisterPhoto
import co.geisyanne.instagram.register.data.RegisterCallback
import co.geisyanne.instagram.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var view: RegisterPhoto.View?,
    private val repository: RegisterRepository
) : RegisterPhoto.Presenter {

    override fun updateUser(photoUri: Uri) {
        view?.showProgress(true)

        repository.updateUser(photoUri, object : RegisterCallback {
            override fun onSuccess() {
                view?.onUpdateSuccess()
            }

            override fun onFailure(message: String) {
                view?.onUpdateFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })

    }

    override fun onDestroy() {
        view = null
    }
}