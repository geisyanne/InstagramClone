package co.geisyanne.instagram.register

import androidx.annotation.StringRes
import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView

interface RegisterNamePassword {

    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayPasswordFailure(@StringRes passwordError: Int?)
        fun onCreateFailure(message: String) // FALHA SERVIDOR
        fun onCreateSuccess(name: String) // SUCESSO > PROX TELA
    }

    interface Presenter : BasePresenter {
        fun createAccount(email: String, name: String, password: String, confirm: String)
    }

}
