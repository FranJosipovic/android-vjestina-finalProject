package agency.five.codebase.android.ui.signup

import agency.five.codebase.android.data.user.UserRepository
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val hasUser: Boolean get() = userRepository.hasUser()

    var signupUiState by mutableStateOf(SignupUiState())
        private set

    fun onEmailChange(email: String) {
        signupUiState = signupUiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        signupUiState = signupUiState.copy(password = password)
    }

    fun onConfirmedPasswordChange(confirmedPassword: String) {
        signupUiState = signupUiState.copy(confirmedPassword = confirmedPassword)
    }

    private fun validateSignupForm() =
        signupUiState.email.isNotBlank() &&
                signupUiState.password.isNotBlank() &&
                signupUiState.confirmedPassword.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignupForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            signupUiState = signupUiState.copy(isLoading = true)
            if (signupUiState.password !=
                signupUiState.confirmedPassword
            ) {
                throw IllegalArgumentException(
                    "Password do not match"
                )
            }
            signupUiState = signupUiState.copy(error = null)
            userRepository.createUser(
                signupUiState.email,
                signupUiState.password,
                context
            ) { isSuccessful, errMessage ->
                signupUiState = if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    signupUiState.copy(isSuccessSignup = true)
                } else {
                    try {
                        Toast.makeText(
                            context,
                            "Failed Login",
                            Toast.LENGTH_SHORT
                        ).show()
                        signupUiState.copy(isSuccessSignup = false)
                        throw Exception(errMessage)
                    } catch (e: Exception) {
                        signupUiState.copy(error = e.message)
                    }
                }
            }
        } catch (e: Exception) {
            signupUiState = signupUiState.copy(error = e.localizedMessage)
            e.printStackTrace()
        } finally {
            delay(4000)
            signupUiState = signupUiState.copy(isLoading = false)
        }
    }
}
