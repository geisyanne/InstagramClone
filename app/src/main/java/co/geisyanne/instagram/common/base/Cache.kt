package co.geisyanne.instagram.common.base

interface Cache<T> {

    fun isCached() : Boolean  // VERIFICAR SE TEM CACHE
    fun get(key: String) : T?  // RETORNAR UM DADO
    fun put(data: T?)  // ADICIONAR UM DADO

}