package co.geisyanne.instagram.splash.data

interface SplashDataSource {
    fun session(callback: SplashCallback)
}