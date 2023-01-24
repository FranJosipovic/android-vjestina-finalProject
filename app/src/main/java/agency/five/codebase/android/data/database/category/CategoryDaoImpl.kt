package agency.five.codebase.android.data.database.category

import agency.five.codebase.android.model.Category
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

const val CATEGORIES_COLLECTION = "categories"

class CategoryDaoImpl : CategoryDao {

    private val db = Firebase.firestore

    private val categoriesRef = db.collection(CATEGORIES_COLLECTION)

    override fun getCategories(userId: String): Flow<List<Category>> = callbackFlow {
        val registration = Firebase.firestore.collection("categories")
            .whereEqualTo("user_id", userId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    cancel()
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val data = snapshot.documents.map { documentSnapshot ->
                        Category(
                            title = documentSnapshot["title"] as String,
                            id = documentSnapshot.id,
                            user_id = documentSnapshot["user_id"] as String
                        )
                    }
                    trySend(data)
                }
            }
        awaitClose { registration.remove() }
    }

    override fun getCategoryById(categoryId: String): Flow<Category> {
        return flow {

            val result =
                categoriesRef.document(categoryId).get().await().toObject(DBCategory::class.java)
            emit(Category(title = result!!.title, id = result.documentId, user_id = result.user_id))
        }
    }

    override fun insertCategory(userId: String, title: String) {
        val data = hashMapOf(
            "title" to title,
            "user_id" to userId
        )
        db.collection(CATEGORIES_COLLECTION).add(data)
    }

    override fun removeCategory(categoryId: String) {
        db.collection(CATEGORIES_COLLECTION).document(categoryId).delete()
    }
}
