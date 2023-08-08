package co.geisyanne.instagram.login.presentation

import android.util.Patterns
import co.geisyanne.instagram.R
import co.geisyanne.instagram.login.Login
import co.geisyanne.instagram.login.data.LoginCallback
import co.geisyanne.instagram.login.data.LoginRepository

class LoginPresenter(
    private var view: Login.View?,
    private val repository: LoginRepository
) : Login.Presenter {

    // VALIDAÇÃO DE FORMULÁRIO
    override fun login(email: String, password: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length >= 8

        if (!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
        } else {
            view?.displayEmailFailure(null)
        }

        if (!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else {
            view?.displayPasswordFailure(null)
        }

        // APÓS VALIDAÇÃO OK
        if (isEmailValid && isPasswordValid) {
            view?.showProgress(true)

            repository.login(
                email,
                password,
                object :
                    LoginCallback {  // OBJECT PARA SOBRESCREVER OS MÉTODOS  // BLOCO ASSÍNCRONO
                    override fun onSuccess() {
                        view?.onUserAuthenticated()
                    }

                    override fun onFailure(message: String) {
                        view?.onUserUnauthorized(message)
                    }

                    override fun onComplete() {
                        view?.showProgress(false)
                    }

                })
        }
    }

    override fun onDestroy() {
        view = null
    }
}