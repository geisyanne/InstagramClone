package co.geisyanne.instagram.register.data

import android.net.Uri

interface RegisterDataSource {
    fun createEmail(email: String, callback:RegisterCallback)

    fun createAccount(email: String, name: String, password: String, callback: RegisterCallback)

    fun updateUser(photoUri: Uri, callback: RegisterCallback)
}