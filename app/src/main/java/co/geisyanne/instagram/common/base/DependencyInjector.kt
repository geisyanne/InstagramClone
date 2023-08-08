package co.geisyanne.instagram.common.base

import android.content.Context
import co.geisyanne.instagram.add.data.AddLocalDataSource
import co.geisyanne.instagram.add.data.AddRepository
import co.geisyanne.instagram.add.data.FireAddDataSource
import co.geisyanne.instagram.home.data.FeedMemoryCache
import co.geisyanne.instagram.home.data.HomeDataSourceFactory
import co.geisyanne.instagram.home.data.HomeRepository
import co.geisyanne.instagram.login.data.FireLoginDataSource
import co.geisyanne.instagram.login.data.LoginRepository
import co.geisyanne.instagram.post.data.PostLocalDataSource
import co.geisyanne.instagram.post.data.PostRepository
import co.geisyanne.instagram.profile.data.PostListMemoryCache
import co.geisyanne.instagram.profile.data.ProfileDataSourceFactory
import co.geisyanne.instagram.profile.data.ProfileMemoryCache
import co.geisyanne.instagram.profile.data.ProfileRepository
import co.geisyanne.instagram.register.data.FireRegisterDataSource
import co.geisyanne.instagram.register.data.RegisterRepository
import co.geisyanne.instagram.search.data.FireSearchDataSource
import co.geisyanne.instagram.search.data.SearchRepository
import co.geisyanne.instagram.splash.data.FireSplashDataSource
import co.geisyanne.instagram.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository() : SplashRepository {
        return SplashRepository(FireSplashDataSource())
    }

    fun loginRepository() : LoginRepository {
        return LoginRepository(FireLoginDataSource())
    }

    fun searchRepository() : SearchRepository {
        return SearchRepository(FireSearchDataSource())
    }

    fun registerRepository() : RegisterRepository {
        return RegisterRepository(FireRegisterDataSource())
    }

    fun profileRepository() : ProfileRepository {
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }

    fun homeRepository() : HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

    fun addRepository() : AddRepository {
        return AddRepository(FireAddDataSource(), AddLocalDataSource())
    }

    fun postRepository(context: Context) : PostRepository {
        return PostRepository(PostLocalDataSource(context))
    }

}