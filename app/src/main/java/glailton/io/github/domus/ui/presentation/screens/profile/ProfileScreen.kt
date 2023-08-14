package glailton.io.github.domus.ui.presentation.screens.profile

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
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
import glailton.io.github.domus.ui.presentation.utils.TestTags

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.profile_title),
                onBack = { onBack.invoke() }
            )
        },
        content = {
            ProfileScreenContent(viewModel)
        }
    )
}

@Composable
fun ProfileScreenContent(viewModel: ProfileViewModel) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var imageUrl = ""

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            imageUri?.let {
                viewModel.saveImage(imageUri)
            }
        }

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }

    val state = viewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colors.surface, CircleShape)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .testTag(TestTags.LOADING_DIALOG),
                    color = MaterialTheme.colors.primary,
                    strokeWidth = 6.dp
                )
            } else {
                AsyncImage(
                    ImageRequest
                        .Builder(context)
                        .data(state.photoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Autumn Collection photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(200.dp)
                )
            }
            Button(
                onClick = { galleryLauncher.launch("image/*") },
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
            onValueChanged = { viewModel.updateInfo(name = it) },
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
            onValueChanged = { viewModel.updateInfo(email = it) },
            keyboardType = KeyboardType.Email,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            imeAction = ImeAction.Next
        )

        TransparentTextField(
            textFieldValue = state.phoneNumber,
            textLabel = stringResource(R.string.phone_number),
            onValueChanged = { viewModel.updateInfo(phoneNumber = it) },
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
            onValueChanged = { viewModel.updateInfo(birthday = it) },
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
            displayProgressBar = state.isLoading,
            onClick = {
                viewModel.updateUser()
            }
        )
    }
}