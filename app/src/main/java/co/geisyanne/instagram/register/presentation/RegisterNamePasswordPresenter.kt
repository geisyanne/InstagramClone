package co.geisyanne.instagram.register.presentation

import co.geisyanne.instagram.R
import co.geisyanne.instagram.register.RegisterNamePassword
import co.geisyanne.instagram.register.data.RegisterCallback
import co.geisyanne.instagram.register.data.RegisterRepository

class RegisterNamePasswordPresenter(
    private var view: RegisterNamePassword.View?,
    private val repository: RegisterRepository
) : RegisterNamePassword.Presenter {

    override fun createAccount(email: String, name: String, password: String, confirm: String) {
        val isNameValid = name.length > 2
        val isPasswordValid = password.length > 7
        val isConfirmValid = password == confirm

        if (!isNameValid) {
            view?.displayNameFailure(R.string.invalid_name)
        } else {
            view?.displayNameFailure(null)
        }

        if (!isPasswordValid) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else {
            view?.displayPasswordFailure(null)
            if (!isConfirmValid) {
                view?.displayPasswordFailure(R.string.password_not_equal)
            } else {
                view?.displayPasswordFailure(null)
            }
        }

        if (isNameValid && isPasswordValid && isConfirmValid) {
            view?.showProgress(true)

            repository.createAccount(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSuccess(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
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