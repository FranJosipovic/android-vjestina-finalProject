package agency.five.codebase.android.ui.signup

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
fun SignupScreenRoute(
    viewModel: SignupViewModel,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit
) {
    val signupUiState = viewModel.signupUiState
    val hasUser = viewModel.hasUser

    SignUpScreen(
        signupUiState = signupUiState,
        hasUser = hasUser,
        onConfirmedPasswordChange = { confirmedPassword ->
            viewModel.onConfirmedPasswordChange(
                confirmedPassword
            )
        },
        onPasswordChange = { password -> viewModel.onPasswordChange(password) },
        onEmailChange = { email -> viewModel.onEmailChange(email) },
        createUser = { context -> viewModel.createUser(context) },
        onNavToHomePage = onNavToHomePage,
        onNavToLoginPage = onNavToLoginPage
    )

}

@Composable
fun SignUpScreen(
    signupUiState: SignupUiState,
    hasUser: Boolean,
    onPasswordChange: (String) -> Unit,
    onConfirmedPasswordChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    createUser: (Context) -> Unit,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,
) {
    val isError = signupUiState.error != null
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
            text = "Sign Up",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary
        )

        if (isError) {
            Text(
                text = signupUiState.error ?: "unknown error",
                color = Color.Red,
            )
        }

        EmailInput(signupUiState.email, onEmailChange, isError)

        PasswordInput(signupUiState.password, onPasswordChange, isError)

        PasswordInput(signupUiState.confirmedPassword, onConfirmedPasswordChange, isError)

        Button(onClick = { createUser(context) }) {
            Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Already have an Account?")
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Sign In",
                modifier = Modifier.clickable { onNavToLoginPage() },
                color = MaterialTheme.colors.primary
            )
        }

        if (signupUiState.isLoading) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = hasUser) {
            if (hasUser) {
                onNavToHomePage()
            }
        }
    }
}



