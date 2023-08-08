package co.geisyanne.instagram.splash.presentation

import co.geisyanne.instagram.splash.Splash
import co.geisyanne.instagram.splash.data.SplashCallback
import co.geisyanne.instagram.splash.data.SplashRepository

class SplashPresenter(
    private var view: Splash.View?,
    private val repository: SplashRepository
) : Splash.Presenter {

    override fun authenticated() {
        repository.session(object : SplashCallback {
            override fun onSuccess() {
                view?.goToMainScreen()
            }

            override fun onFailure() {
                view?.goToLoginScreen()
            }

        })
    }

    override fun onDestroy() {
        view = null
    }
}