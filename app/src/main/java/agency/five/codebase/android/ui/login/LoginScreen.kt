package agency.five.codebase.android.ui.login

import agency.five.codebase.android.ui.component.EmailInput
import agency.five.codebase.android.ui.component.PasswordInput
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreenRoute(
    loginViewModel: LoginViewModel,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit,
) {
    val loginUiState = loginViewModel.loginUiState
    val hasUser = loginViewModel.hasUser
    LoginScreen(
        loginUiState = loginUiState,
        hasUser = hasUser,
        onEmailChange = { email: String -> loginViewModel.onEmailChange(email) },
        onPasswordChange = { password: String -> loginViewModel.onPasswordChange(password) },
        loginUser = { context -> loginViewModel.loginUser(context) },
        onNavToHomePage = onNavToHomePage,
        onNavToSignUpPage = onNavToSignUpPage,
    )
}

@Composable
fun LoginScreen(
    loginUiState: LoginUiState,
    hasUser: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    loginUser: (Context) -> Unit,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit,
) {
    val isError = loginUiState.error != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "TodoDo",
            modifier = Modifier.padding(vertical = 30.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary
        )

        if (isError) {
            Text(
                text = loginUiState.error ?: "unknown error",
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }

        EmailInput(value = loginUiState.email, onEmailChange = onEmailChange, isError = isError)
        PasswordInput(
            value = loginUiState.password,
            onPasswordChange = onPasswordChange,
            isError = isError
        )

        Button(onClick = { loginUser(context) }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Don't have an Account?")
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "SignUp",
                modifier = Modifier.clickable { onNavToSignUpPage() },
                color = MaterialTheme.colors.primary
            )
        }

        if (loginUiState.isLoading) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = hasUser) {
            if (hasUser) {
                onNavToHomePage()
            }
        }
    }
}
