package agency.five.codebase.android.data.database

import agency.five.codebase.android.model.Category
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(category: DBCategory)

    @Delete
    fun deleteCategory(category: DBCategory)

    @Query("SELECT * FROM categories WHERE user_id = :userId")
    fun categories(userId: String): Flow<List<DBCategory>>

    @Query("SELECT * FROM categories WHERE id=:categoryId AND user_id =:userId")
    fun categoryById(categoryId: Int, userId: String): Flow<Category>
}
