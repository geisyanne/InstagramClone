package co.geisyanne.instagram.profile.data

import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.Post
import co.geisyanne.instagram.common.model.User

interface ProfileDataSource {

    fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<User, Boolean?>>)

    fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)

    fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) { throw UnsupportedOperationException() }

    fun fetchSession() : String  {throw UnsupportedOperationException()}

    fun putUser(response: Pair<User, Boolean?>?) {throw UnsupportedOperationException()} // COLOCAR NO CACHE

    fun putPosts(response: List<Post>?) {throw UnsupportedOperationException()} // COLOCAR NO CACHE

}
