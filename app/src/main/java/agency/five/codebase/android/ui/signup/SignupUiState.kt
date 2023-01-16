package agency.five.codebase.android.ui.signup

data class SignupUiState(
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val isSuccessSignup: Boolean = false
)
