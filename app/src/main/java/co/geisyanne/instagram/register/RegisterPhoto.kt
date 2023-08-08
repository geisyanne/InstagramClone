package co.geisyanne.instagram.register

import android.net.Uri
import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView

interface RegisterPhoto {

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun onUpdateFailure(message: String)
        fun onUpdateSuccess()
    }

    interface Presenter : BasePresenter {
        fun updateUser(photoUri: Uri)
    }

}
