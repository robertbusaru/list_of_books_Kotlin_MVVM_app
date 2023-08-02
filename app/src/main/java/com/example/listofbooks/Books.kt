import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Books(
    val bookImage: String,
    val bookName: String,
    val authorImage: String,
    val authorName: String,
    val isbn: String,
    val bookTypeImage: String,
    val bookType: String,
    var favorite: Boolean,
    val description: String
) : Serializable
