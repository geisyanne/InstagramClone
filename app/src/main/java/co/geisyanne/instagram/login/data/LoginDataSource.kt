package co.geisyanne.instagram.login.data

// INTERFACE Q VAI IMPLEMENTAR A CHAMADA NO SERVIDOR
interface LoginDataSource {
    fun login(email: String, password: String, callback: LoginCallback)

}