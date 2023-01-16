package agency.five.codebase.android.ui.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser

@Composable
fun Drawer(viewModel: DrawerViewModel, onNavigateToSignUpScreen: () -> Unit) {

    val currentUser: FirebaseUser? by viewModel.currentUser.collectAsState()

    val email: String? = currentUser?.email

    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (email != null) {
            Text(text = email, modifier = Modifier.padding(10.dp))
        }
    }
    Divider()
    Button(
        onClick = {
            viewModel.signOut()
            onNavigateToSignUpScreen()
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(text = "Log out")
    }
}
