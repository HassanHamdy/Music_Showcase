import android.graphics.Bitmap

data class MusicModel (

//	val id : Int = 0,
	val type : String = "",
	val title : String = "",
	val publishingDate : String = "",
	val duration : Int = 0,
//	val label : String = "",
	val mainArtist : MainArtist,
//	val mainRelease : Boolean = false,
//	val streamableTracks : Int = 0,
//	val numberOfTracks : Int = 0,
//	val additionalArtists : List<String> = emptyList(),
//	val genres : List<String> = emptyList(),
//	val streamable : Boolean = false,
//	val partialStreamable : Boolean = false,
//	val adfunded : Boolean = false,
//	val bundleOnly : Boolean = false,
	val cover : Cover,
	val bitmapImage: Bitmap
//	val variousArtists : Boolean = false
)