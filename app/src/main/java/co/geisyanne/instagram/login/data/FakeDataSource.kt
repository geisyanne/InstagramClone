package co.geisyanne.instagram.login.data

import android.os.Handler
import android.os.Looper
import co.geisyanne.instagram.common.model.Database

class FakeDataSource : LoginDataSource {  // SIMULA CONSULTA NO BANCO DE DADOS 'Database'
    override fun login(email: String, password: String, callback: LoginCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = Database.usersAuth.firstOrNull { email == it.email }

            if (userAuth == null) {
                callback.onFailure("Usuário não encontrado")
            } else if (userAuth.password != password) {
                callback.onFailure("Senha incorreta")
            } else {
                Database.sessionAuth = userAuth
                callback.onSuccess() // (userAuth)
            }
            callback.onComplete()
        }, 2000)
    }
}