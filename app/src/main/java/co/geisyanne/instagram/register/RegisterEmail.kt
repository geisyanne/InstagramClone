package co.geisyanne.instagram.register

import androidx.annotation.StringRes
import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView

interface RegisterEmail {

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun onEmailFailure(message: String)
        fun goToNameAndPasswordScreen(email: String) // SUCESSO > PROX TELA
    }

    interface Presenter : BasePresenter {
        fun createEmail(email: String)
    }

}