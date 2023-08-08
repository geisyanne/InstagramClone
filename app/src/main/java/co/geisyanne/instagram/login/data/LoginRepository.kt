package co.geisyanne.instagram.login.data

class LoginRepository(private val dataSource: LoginDataSource) {

    fun login(email: String, password: String, callback: LoginCallback) {
        dataSource.login(email, password, callback) // CHAMADA NO SERVIDOR ~ DEVOLVE CALLBACK
    }

}