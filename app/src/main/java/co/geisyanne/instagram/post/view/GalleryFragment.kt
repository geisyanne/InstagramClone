package co.geisyanne.instagram.post.view

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import co.geisyanne.instagram.R
import co.geisyanne.instagram.common.base.BaseFragment
import co.geisyanne.instagram.common.base.DependencyInjector
import co.geisyanne.instagram.common.model.Picture
import co.geisyanne.instagram.databinding.FragmentGalleryBinding
import co.geisyanne.instagram.post.Post
import co.geisyanne.instagram.post.presentation.PostPresenter

class GalleryFragment : BaseFragment<FragmentGalleryBinding, Post.Presenter>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
), Post.View {

    override lateinit var presenter: Post.Presenter

    private val adapter = PictureAdapter() { uri ->
        binding?.galleryImgSelected?.setImageURI(uri)
        binding?.galleryNested?.smoothScrollTo(0, 0)

        presenter.selectUri(uri)
    }

    override fun setupPresenter() {
        presenter = PostPresenter(this, DependencyInjector.postRepository(requireContext()))
    }

    override fun getMenu(): Int = R.menu.menu_send

    override fun setupViews() {
        binding?.galleryRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.galleryRv?.adapter = adapter

        presenter.fetchPictures()
    }

    override fun showProgress(enabled: Boolean) {
        binding?.galleryProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullPictures(posts: List<Picture>) {
        binding?.galleryTxtEmpty?.visibility = View.GONE
        binding?.galleryRv?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
        binding?.galleryImgSelected?.setImageURI(posts.first().uri)  // PRÉ-SELECIONAR PRIMEIRA IMG
        binding?.galleryNested?.smoothScrollTo(0, 0)  // ROLAR PRA CIMA

        presenter.selectUri(posts.first().uri)  // PARA GUARDAR URI DA PRIMEIRA IMG
    }

    override fun displayEmptyPictures() {
        binding?.galleryTxtEmpty?.visibility = View.VISIBLE
        binding?.galleryRv?.visibility = View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {  // PARA COMPARTILHA A FOTO, ENVIANDO PARA O PROX FRAG
        when (item.itemId) {
            R.id.action_send -> {
                setFragmentResult(
                    "takePhotoKey",
                    bundleOf("uri" to presenter.getSelectedUri())
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

