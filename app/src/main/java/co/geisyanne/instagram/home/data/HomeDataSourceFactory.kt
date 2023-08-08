package co.geisyanne.instagram.home.data

import co.geisyanne.instagram.common.base.Cache
import co.geisyanne.instagram.common.model.Post


class HomeDataSourceFactory(  // FORNECEDOR DE DATASOURCE
    private val feedCache: Cache<List<Post>>
) {

    fun createLocalDataSource(): HomeDataSource {
        return HomeLocalDataSource(feedCache)
    }

    fun createRemoteDataSource(): HomeDataSource {
        return FireHomeDataSource()
    }

    fun createFromFeed(): HomeDataSource {
        if (feedCache.isCached()) {  // SE TIVER, É PRA BUSCAR EM MEMÓRIA
            return HomeLocalDataSource(feedCache)
        }
        return FireHomeDataSource()  // SE NÃO, É DO SERVIDOR
    }

}