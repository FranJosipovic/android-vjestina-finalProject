package agency.five.codebase.android.ui.homeCategory

import agency.five.codebase.android.data.todo.TodoFirestoreRepository
import agency.five.codebase.android.model.Category
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeCategoryViewModel(private val todoFirestoreRepository: TodoFirestoreRepository) : ViewModel() {

    private val userId: String
        get() = todoFirestoreRepository.getUserId()

    val categories: StateFlow<List<Category>> = todoFirestoreRepository.categories.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList()
    )

    fun addCategory(categoryTitle: String) {
        viewModelScope.launch {
            todoFirestoreRepository.addCategory(categoryTitle, userId)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            todoFirestoreRepository.deleteCategory(category,userId)
        }
    }
}
