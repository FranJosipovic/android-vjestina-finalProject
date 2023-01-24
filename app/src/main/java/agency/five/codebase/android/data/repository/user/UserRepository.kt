package agency.five.codebase.android.data.repository.user

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow

interface UserRepository {
    val currentUser: MutableStateFlow<FirebaseUser?>

    fun hasUser(): Boolean

    fun getUserId(): String

    fun getCurrentUser(): FirebaseUser?

    suspend fun signOut()

    suspend fun createUser(
        email: String,
        password: String,
        context: Context,
        onComplete: (Boolean, String?) -> Unit
    )

    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ): Task<AuthResult>

}
