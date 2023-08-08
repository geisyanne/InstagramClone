package co.geisyanne.instagram.common.model

// DATACLAS DE USER NO FIREBASE, TEM Q SER INICIALIZADO
data class User(

    val uuid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val postCount: Int = 0,
    val followers: Int = 0,
    val following: Int = 0
)