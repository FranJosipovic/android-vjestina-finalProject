package agency.five.codebase.android.ui.homeCategory

import agency.five.codebase.android.data.repository.category.CategoryRepository
import agency.five.codebase.android.data.repository.todo.TodoRepository
import agency.five.codebase.android.data.repository.user.UserRepository
import agency.five.codebase.android.model.Category
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeCategoryViewModel(
    private val categoryRepository: CategoryRepository,
) :  ViewModel() {
    val categories: StateFlow<List<Category>> = categoryRepository.categories.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList()
    )

    fun addCategory(categoryTitle: String) {
        viewModelScope.launch {
            categoryRepository.addCategory(categoryTitle)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
}
