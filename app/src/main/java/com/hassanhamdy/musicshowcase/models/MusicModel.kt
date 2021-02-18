import android.graphics.Bitmap

data class MusicModel(
	val type: String = "",
	val title: String = "",
	val publishingDate: String = "",
	val duration: Int = 0,
	val mainArtist: MainArtist,
	val cover: Cover,
	val bitmapImage: Bitmap?
)