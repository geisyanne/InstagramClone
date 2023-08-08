package co.geisyanne.instagram.profile.data

import android.os.Handler
import android.os.Looper
import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.Database
import co.geisyanne.instagram.common.model.Database.sessionAuth
import co.geisyanne.instagram.common.model.Post
import co.geisyanne.instagram.common.model.User

class ProfileFakeRemoteDataSource : ProfileDataSource {

    override fun fetchUserProfile(
        userUUID: String,
        callback: RequestCallback<Pair<User, Boolean?>>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid }

            if (userAuth != null) {
                if (userAuth == Database.sessionAuth) {  // SE USER É IGUAL AO DA SESSÃO = ACESSO DE PERFIL PRÓPRIO
                    // callback.onSuccess(Pair(userAuth, null)) // DEVOLVER PRÓPIO USER
                } else {  // SE NÃO = BUSCA DE USERS
                    val followings =
                        Database.followers[Database.sessionAuth!!.uuid]  // PEGAR SEGUIDORES DO USER DA SESSÃO

                    val destUser =
                        followings?.firstOrNull { it == userUUID } // VERIFICAR SE O ID PESQUISADO TÁ NA TABELA DOS SEGUIDORES DO USER DA SESSÃO
                    // destUser != NULL  > TRUE = SEGUINDO | destUser == NULL > FALSE = SEGUIR
                    // callback.onSuccess(Pair(userAuth, destUser != null))  // RESPOSTA BOOLEANA
                }

            } else {
                callback.onFailure("Usuário não encontrado")
            }
            callback.onComplete()
        }, 2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val posts = Database.posts[userUUID]

            callback.onSuccess(posts?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }

    override fun followUser(
        userUUID: String,
        isFollow: Boolean,
        callback: RequestCallback<Boolean>
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            var followers =
                Database.followers[sessionAuth!!.uuid]  // PASSANDO OS SEGUIDORES DO USER LOGADO

            if (followers == null) { // SE A TABELA DE SEGUIDORES FOR NULA
                followers = mutableSetOf() // CRIAR UMA NOVA
                Database.followers[sessionAuth!!.uuid] = followers // PASSAR PARA O USER LOGADO
            }

            if (isFollow) { // SE TRUE, SE FEZ O CLICK DE SEGUIR
                Database.followers[sessionAuth!!.uuid]!!.add(userUUID)  // ADC
            } else {  // SE FALSE, SE FEZ O CLICK DE DE DEIXAR DE SEGUIR
                Database.followers[sessionAuth!!.uuid]!!.remove(userUUID)  // REMOVE
            }

            callback.onSuccess(true)
            callback.onComplete()

        }, 500)
    }
}