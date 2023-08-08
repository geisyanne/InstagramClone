package co.geisyanne.instagram.splash

import co.geisyanne.instagram.common.base.BasePresenter
import co.geisyanne.instagram.common.base.BaseView

interface Splash {

    interface Presenter : BasePresenter {
        fun authenticated()
    }

    interface View : BaseView<Presenter> {
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}