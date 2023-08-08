package co.geisyanne.instagram.common.model

import java.util.*

// BANCO DE DADOS FAKE
object Database {

    val usersAuth = mutableListOf<UserAuth>()
    val posts =
        hashMapOf<String, MutableSet<Post>>()  // PARA CADA ID, UMA COLEÇÃO DE POSTS MUTÁVEL > "userA" : ["post1", "post2"]
    val feeds = hashMapOf<String, MutableSet<Post>>()
    val followers = hashMapOf<String, MutableSet<String>>()  // UM USUÁRIO, TEM N SEGUIDORES

    var sessionAuth: UserAuth? = null  // ARMAZENAR SESSÃO DO USER

    init {  // BLOCO DE INICIALIZAÇÃO // DADOS FALSOS DE USUÁRIOS

        val userA = UserAuth(UUID.randomUUID().toString(), "UserA", "userA@gmail.com", "12345678", null)
//        )
//        val userB = UserAuth(
//            UUID.randomUUID().toString(), "UserB", "userB@gmail.com", "87654321",
//            Uri.fromFile(File("/storage/emulated/0/Android/media/co.geisyanne.instagram/Instagram/2023-07-19-16-15-57-727.jpg"))
//        )
//
       usersAuth.add(userA)
//        usersAuth.add(userB)
//
//        followers[userA.uuid] = hashSetOf()
//        posts[userA.uuid] = hashSetOf()
//        feeds[userA.uuid] = hashSetOf()
//
//        followers[userB.uuid] = hashSetOf()
//        posts[userB.uuid] = hashSetOf()
//        feeds[userB.uuid] = hashSetOf()
//
//        for(i in 0..30) { // USERS
//            val user = UserAuth(UUID.randomUUID().toString(), "User$i", "user$i@gmail.com", "12345678", null)
//            usersAuth.add(user)
//        }

       // sessionAuth = usersAuth.first()



    }

}