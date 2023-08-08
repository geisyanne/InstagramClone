package co.geisyanne.instagram.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.geisyanne.instagram.R
import co.geisyanne.instagram.common.model.Post
import com.bumptech.glide.Glide

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    var items: List<Post> = mutableListOf()
    private var isClicked: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {

            Glide.with(itemView.context).load(post.photoUrl)
                .into(itemView.findViewById(R.id.home_img_post))  // CAMINHO NA NET
            Glide.with(itemView.context).load(post.publisher?.photoUrl)
                .into(itemView.findViewById(R.id.home_img_user))
            //itemView.findViewById<ImageView>(R.id.home_img_post).setImageURI(post.uri) // LOCALMENTE, CAMINHO DENTRO DO APARELHO

            itemView.findViewById<TextView>(R.id.home_txt_desc).text = post.caption
            itemView.findViewById<TextView>(R.id.home_txt_username).text = post.publisher?.name
            // itemView.findViewById<ImageView>(R.id.home_img_user).setImageURI(post.publisher.photoUri)

            val imgBtnHeart = itemView.findViewById<ImageButton>(R.id.home_btn_heart)

            imgBtnHeart.setOnClickListener {
                isClicked = if (!isClicked) {
                    imgBtnHeart.setImageResource(R.drawable.ic_insta_heart)
                    true
                } else {
                    imgBtnHeart.setImageResource(R.drawable.ic_insta_heart_clicked)
                    false
                }
            }
        }
    }
}