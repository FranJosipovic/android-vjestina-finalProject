package agency.five.codebase.android.data.repository.category

import agency.five.codebase.android.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository{
    val categories: Flow<List<Category>>
    suspend fun addCategory(categoryTitle: String)
    fun categoryById(categoryId: String): Flow<Category>
    suspend fun deleteCategory(category: Category)
}
