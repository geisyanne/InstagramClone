package co.geisyanne.instagram.post.view

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import co.geisyanne.instagram.R
import co.geisyanne.instagram.common.model.Picture

class PictureAdapter(private val onClick: (Uri) -> Unit) :
    RecyclerView.Adapter<PictureAdapter.PostViewHolder>() {

    var items: List<Picture> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_profile_grid, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Picture) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val bitmap =
                    itemView.context.contentResolver.loadThumbnail(image.uri, Size(200, 200), null)
                itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageBitmap(bitmap)
            } else {
                MediaStore.Images.Thumbnails.getThumbnail(
                    itemView.context.contentResolver,
                    image.id,
                    MediaStore.Images.Thumbnails.MINI_KIND,
                    null
                )
            }
            itemView.setOnClickListener {
                onClick.invoke(image.uri)
            }
        }
    }
}