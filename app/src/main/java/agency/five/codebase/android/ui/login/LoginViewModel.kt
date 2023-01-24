package agency.five.codebase.android.ui.login

import agency.five.codebase.android.data.repository.user.UserRepository
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    val hasUser: Boolean
        get() = userRepository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(email: String) {
        loginUiState = loginUiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }

    private fun validateLoginForm() =
        loginUiState.email.isNotBlank() &&
                loginUiState.password.isNotBlank()

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(error = null)
            userRepository.login(
                loginUiState.email,
                loginUiState.password
            ) { isSuccessful, errMessage ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    try {
                        Toast.makeText(
                            context,
                            "Failed Login",
                            Toast.LENGTH_SHORT
                        ).show()
                        loginUiState.copy(isSuccessLogin = false)
                        throw Exception(errMessage)
                    } catch (e: Exception) {
                        loginUiState.copy(error = e.message)
                    }
                }
            }
        } catch (e: Exception) {
            loginUiState = loginUiState.copy(error = e.localizedMessage)
            e.printStackTrace()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            loginUiState = loginUiState.copy(error = e.localizedMessage)
            e.printStackTrace()
        } finally {
            delay(4000)
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }
}


