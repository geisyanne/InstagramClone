package co.geisyanne.instagram.login

import androidx.annotation.StringRes
import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView

interface Login {

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onUserAuthenticated()
        fun onUserUnauthorized(message: String)
    }

    interface Presenter : BasePresenter {
        fun login(email: String, password: String)
    }
}