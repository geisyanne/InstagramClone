package co.geisyanne.instagram.register.data

import android.net.Uri

class RegisterRepository(private val dataSource: RegisterDataSource) {

    fun createEmail(email: String, callback: RegisterCallback) {
        dataSource.createEmail(email, callback)
    }

    fun createAccount(email: String, name: String, password: String, callback: RegisterCallback) {
        dataSource.createAccount(email,name, password, callback)
    }

    fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        dataSource.updateUser(photoUri, callback)
    }

}
