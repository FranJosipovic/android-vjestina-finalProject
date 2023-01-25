package agency.five.codebase.android.ui.drawer

import agency.five.codebase.android.data.repository.user.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DrawerViewModel(private val userRepository: UserRepository) : ViewModel() {
    val currentUser: StateFlow<FirebaseUser?> = userRepository.currentUser
    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
        }
    }
}
