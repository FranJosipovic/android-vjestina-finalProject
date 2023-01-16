package agency.five.codebase.android.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(todo: DBTodo)

    @Delete
    fun deleteTodo(todo: DBTodo)

    @Query("DELETE FROM todos WHERE category_id = :categoryId")
    fun deleteAllById(categoryId: Int)

    @Query("SELECT * FROM todos WHERE user_id =:userId")
    fun todos(userId: String): Flow<List<DBTodo>>

    @Query("SELECT * FROM todos WHERE category_id =:categoryId AND user_id=:userId")
    fun getTodosByCategory(categoryId: Int, userId: String): Flow<List<DBTodo>>

    @Update
    suspend fun updateItem(item: DBTodo)
}
