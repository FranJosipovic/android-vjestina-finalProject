package agency.five.codebase.android.data.repository.category

import agency.five.codebase.android.data.database.category.CategoryDao
import agency.five.codebase.android.data.repository.user.UserRepository
import agency.five.codebase.android.model.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val userRepository: UserRepository,
    private val categoryDao: CategoryDao,
    private val bgDispatcher: CoroutineDispatcher
) : CategoryRepository {

    override val categories: Flow<List<Category>> = userRepository.currentUser.flatMapLatest {
        categoryDao.getCategories(it?.uid.orEmpty())
    }

    override fun categoryById(categoryId: String): Flow<Category> {
        return categoryDao.getCategoryById(categoryId = categoryId)
    }

    override suspend fun deleteCategory(category: Category) {
        withContext(bgDispatcher) {
            categoryDao.removeCategory(categoryId = category.id)
        }
    }

    override suspend fun addCategory(categoryTitle: String) {
        withContext(bgDispatcher) {
            categoryDao.insertCategory(
                userId = userRepository.currentUser.value?.uid.orEmpty(), title = categoryTitle
            )
        }
    }
}
