package co.geisyanne.instagram.post.data

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import co.geisyanne.instagram.common.model.Picture

class PostLocalDataSource(private val context: Context) : PostDataSource {

    // JÁ ESTÁ SENDO CHAMADO EM BACKGROUND (OU COROTINAS) ROTINA PARALELA
    override suspend fun fetchPictures(): List<Picture> {
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {  // Q = 29
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else { // < 29
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT
        )

        val pictures = mutableListOf<Picture>()

        context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Images.Media._ID} DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                pictures.add(Picture(uri, id))

                if (pictures.size == 99)
                    break
            }
        }

        return pictures
    }
}