package co.geisyanne.instagram.post.presentation

import android.net.Uri
import co.geisyanne.instagram.post.Post
import co.geisyanne.instagram.post.data.PostRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPresenter(
    private var view: Post.View?,
    private val repository: PostRepository
) : Post.Presenter, CoroutineScope {

    private var uri: Uri? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext =
        job + Dispatchers.IO  // TAREFA PARALELA

    override fun fetchPictures() {
        // AQUI NA MAIN (UI)
        view?.showProgress(true)

        launch {
            // AQUI PARALELA (COROUTINE IO)
            val pictures = repository.fetchPictures()

            withContext(Dispatchers.Main) {
                // AQUI EXECUTA DE VOLTA NA MAIN THREAD
                if (pictures.isEmpty()) {
                    view?.displayEmptyPictures()
                } else {
                    view?.displayFullPictures(pictures)
                }
                view?.showProgress(false)
            }
        }

    }

    override fun selectUri(uri: Uri) {
        this.uri = uri  // GUARDAR REF DA URI NUMA VAR DE CAMPO
    }

    override fun getSelectedUri(): Uri? {
        return uri  // RETORNA URI ARMAZENADA
    }

    override fun onDestroy() {
        job.cancel()  // DESTRUIR TAREFA PARALELA
        view = null
    }
}