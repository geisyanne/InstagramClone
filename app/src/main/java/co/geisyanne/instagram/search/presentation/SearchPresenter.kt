package co.geisyanne.instagram.search.presentation

import co.geisyanne.instagram.common.base.RequestCallback
import co.geisyanne.instagram.common.model.User
import co.geisyanne.instagram.search.Search
import co.geisyanne.instagram.search.data.SearchRepository

class SearchPresenter(
    private var view: Search.View?,
    private val repository: SearchRepository
) : Search.Presenter {

    override fun fetchUsers(name: String) {
        view?.showProgress(true)
        repository.fetchUsers(name, object : RequestCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                if (data.isEmpty()) {
                    view?.displayEmptyUsers()
                } else {
                    view?.displayFullUsers(data)
                }
            }

            override fun onFailure(message: String) {
                view?.displayEmptyUsers()
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}