package glailton.io.github.domus.ui.presentation.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import glailton.io.github.domus.R
import glailton.io.github.domus.ui.presentation.components.RoundedButton
import glailton.io.github.domus.ui.presentation.components.TopBar
import glailton.io.github.domus.ui.presentation.components.TransparentTextField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.profile_title)) {
                
            }
        },
        content = {
            ProfileScreenContent(viewModel)
        }
    )
}

@Composable
fun ProfileScreenContent(viewModel: ProfileViewModel) {
    val state = viewModel.state.collectAsState().value
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colors.surface, CircleShape),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                ImageRequest
                    .Builder(context)
                    .data("https://images.unsplash.com/photo-1521676129211-b7a9e7592e65?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80")
                    .crossfade(true)
                    .build(),
                contentDescription = "Autumn Collection photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(200.dp)
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(54.dp)
                    .clip(shape = CircleShape),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.onSurface
                )
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = "Edit")
            }
        }

        TransparentTextField(
            textFieldValue = state.name,
            textLabel = stringResource(id = R.string.name),
            keyboardType = KeyboardType.Text,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            imeAction = ImeAction.Next
        )

        TransparentTextField(
            textFieldValue = state.email,
            textLabel = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            imeAction = ImeAction.Next
        )

        TransparentTextField(
            textFieldValue = state.phoneNumber,
            textLabel = stringResource(R.string.phone_number),
            maxChar = 10,
            keyboardType = KeyboardType.Phone,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            imeAction = ImeAction.Next
        )

        TransparentTextField(
            textFieldValue = state.birthday,
            textLabel = stringResource(id = R.string.birthday),
            keyboardType = KeyboardType.Number,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            imeAction = ImeAction.Next
        )

        RoundedButton(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(id = R.string.save),
            displayProgressBar = false,
            onClick = {

            }
        )
    }
}