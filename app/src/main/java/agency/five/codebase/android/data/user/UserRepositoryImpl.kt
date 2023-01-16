package agency.five.codebase.android.data.user

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
) : UserRepository {
    override var currentUser: MutableStateFlow<FirebaseUser?> =
        MutableStateFlow(Firebase.auth.currentUser)

    override fun getCurrentUser(): FirebaseUser? =
        currentUser.value

    override fun hasUser(): Boolean = Firebase.auth.currentUser != null

    override fun getUserId(): String = currentUser.value?.uid.orEmpty()

    override suspend fun signOut() {
        withContext(dispatcher) {
            Firebase.auth.signOut()
        }
    }

    //pokusao sam obradivati errore tako da ih bacam u viewModel i tamo uhvatim...nisam uspio naci razlog zasto ne radi.
    //pa sam samo slao u obliku stringa
    override suspend fun createUser(
        email: String,
        password: String,
        context: Context,
        onComplete: (Boolean, String?) -> Unit
    ) {
        withContext(dispatcher)
        {
            Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onComplete(true, null)
                        currentUser.update { Firebase.auth.currentUser }
                    } else {
                        onComplete(false, it.exception?.message)
                    }
                }
        }
    }

    override suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) = withContext(dispatcher) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete(true, null)
                    currentUser.update { Firebase.auth.currentUser }
                } else {
                    onComplete(false, it.exception?.message)
                }
            }
    }
}
