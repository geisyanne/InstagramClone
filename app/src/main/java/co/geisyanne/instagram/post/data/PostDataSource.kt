package co.geisyanne.instagram.post.data

import co.geisyanne.instagram.common.model.Picture

interface PostDataSource {
    suspend fun fetchPictures() : List<Picture>  // RETORNO ASSINCRONO, NÃO TEM CALLBACK
    // FUNÇÃO Q VAI SER DISPARADA PARALELAMENTE A FUNÇÃO PRINCIPAL > CORROUTINES
}