package agency.five.codebase.android.data.database

import agency.five.codebase.android.model.Category
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

const val CATEGORIES_COLLECTION = "categories"

class CategoryFirestoreDao {

    private val db = Firebase.firestore

    val categoriesRef = db.collection(CATEGORIES_COLLECTION)

    val categories: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())

    val categoryFlow = MutableSharedFlow<List<Category>>()

    suspend fun getCategories(userId: String): List<Category> {
        val result = categoriesRef.whereEqualTo("user_id", userId).get().await()
            .toObjects(DBCategory::class.java)
        Log.i("smt", result.size.toString())
        return result.map { Category(title = it.title, id = it.id, user_id = it.user_id) }
    }

    fun getCategoryById(categoryId: String): Flow<Category> {
        return flow {
            emit(
                db.collection(CATEGORIES_COLLECTION).document(categoryId).get().await()
                    .toObject(Category::class.java)!!
            )
        }
    }

    fun insertCategory(userId: String, title: String) {
        val data = hashMapOf(
            "title" to title,
            "user_id" to userId
        )
        db.collection(CATEGORIES_COLLECTION).add(data)
    }

    fun removeCategory(categoryId: String) {
        db.collection(CATEGORIES_COLLECTION).document(categoryId).delete()
    }
}
