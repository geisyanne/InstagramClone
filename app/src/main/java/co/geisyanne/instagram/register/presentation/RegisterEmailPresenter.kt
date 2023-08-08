package co.geisyanne.instagram.register.presentation

import android.util.Patterns
import co.geisyanne.instagram.R
import co.geisyanne.instagram.register.RegisterEmail
import co.geisyanne.instagram.register.data.RegisterCallback
import co.geisyanne.instagram.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view: RegisterEmail.View?,
    private val repository: RegisterRepository
) : RegisterEmail.Presenter {

    // VALIDAÇÃO DO EMAIL PARA CADASTRO
    override fun createEmail(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches() // T OR F

        if (!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
        } else {
            view?.displayEmailFailure(null)
        }

        if (isEmailValid) {
            view?.showProgress(true)

            repository.createEmail(email, object : RegisterCallback {
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }

                override fun onFailure(message: String) {
                    view?.onEmailFailure(message)
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