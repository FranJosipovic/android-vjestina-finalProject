package agency.five.codebase.android.ui.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false
)
