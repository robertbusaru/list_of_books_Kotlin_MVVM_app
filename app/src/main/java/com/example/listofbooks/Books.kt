import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books_table")
data class Books(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "bookImage")
    val bookImage: String,

    @ColumnInfo(name = "bookName")
    val bookName: String,

    @ColumnInfo(name = "authorImage")
    val authorImage: String,

    @ColumnInfo(name = "authorName")
    val authorName: String,

    @ColumnInfo(name = "isbn")
    val isbn: String,

    @ColumnInfo(name = "bookTypeImage")
    val bookTypeImage: String,

    @ColumnInfo(name = "bookType")
    val bookType: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean,

    @ColumnInfo(name = "description")
    val description: String
) : Serializable
