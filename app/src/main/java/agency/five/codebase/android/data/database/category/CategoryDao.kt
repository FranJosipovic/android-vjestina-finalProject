package agency.five.codebase.android.data.database.category

import agency.five.codebase.android.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDao {
    fun getCategories(userId: String): Flow<List<Category>>
    fun getCategoryById(categoryId: String): Flow<Category>
    fun insertCategory(userId: String, title: String)
    fun removeCategory(categoryId: String)
}
