package glailton.io.github.domus.ui.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import glailton.io.github.domus.R
import glailton.io.github.domus.ui.presentation.components.EventDialog
import glailton.io.github.domus.ui.presentation.components.RoundedButton
import glailton.io.github.domus.ui.presentation.components.TransparentTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onDismissDialog: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val emailValue = rememberSaveable { mutableStateOf("") }
    val passwordValue = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.login_description),
            contentScale = ContentScale.FillWidth
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        ConstraintLayout {
            val (surface, fab) = createRefs()

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .constrainAs(surface) {
                        bottom.linkTo(parent.bottom)
                    },
                color = Color.White,
                shape = RoundedCornerShape(
                    topStartPercent = 8,
                    topEndPercent = 8
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(R.string.label_welcome),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium)
                    )
                    Text(
                        text = stringResource(R.string.login_account),
                        style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.primary)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp).background(Color.White, CircleShape),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TransparentTextField(
                            textFieldValue = emailValue,
                            textLabel = stringResource(R.string.email),
                            keyboardType = KeyboardType.Email,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            imeAction = ImeAction.Next
                        )

                        TransparentTextField(
                            textFieldValue = passwordValue,
                            textLabel = stringResource(R.string.password),
                            keyboardType = KeyboardType.Password,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    onLogin.invoke(emailValue.value, passwordValue.value)
                                }
                            ),
                            imeAction = ImeAction.Done,
                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisibility = !passwordVisibility
                                }) {
                                    Icon(
                                        imageVector = if (passwordVisibility) {
                                            Icons.Default.Visibility
                                        } else {
                                            Icons.Default.VisibilityOff
                                        },
                                        contentDescription = stringResource(R.string.toggle_password_icon)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisibility) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            }
                        )
                        
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.forgot_password),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.End
                        )
                    }
                    
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RoundedButton(
                            text = stringResource(id = R.string.login),
                            displayProgressBar = state.isLoading,
                            onClick = {
                                onLogin.invoke(emailValue.value, passwordValue.value)
                            }
                        )

                        ClickableText(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.not_have_account))
                                append(
                                    AnnotatedString(
                                        text = stringResource(R.string.sign_up),
                                        spanStyle = SpanStyle(
                                            color = MaterialTheme.colors.primary,
                                            fontWeight = FontWeight.Bold,
                                            shadow = Shadow.None
                                        )
                                    )
                                )
                            },
                            onClick = {
                                onNavigateToRegister.invoke()
                            }
                        )
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .size(72.dp)
                    .constrainAs(fab) {
                        top.linkTo(surface.top, margin = (-24).dp)
                        end.linkTo(surface.end, margin = 32.dp)
                    },
                backgroundColor = MaterialTheme.colors.primary,
                onClick = { onNavigateToRegister.invoke() }
            ) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = stringResource(R.string.icon_description),
                    tint = Color.White
                )
            }
        }
    }

    if(state.loginError){
        EventDialog(
            errorMessage = state.loginErrorMessage ?: R.string.error_input_empty,
            onDismiss = onDismissDialog
        )
    }
}

@Preview
@Composable
fun Prev() {
    LoginScreen(viewModel = koinViewModel(), {_, _ -> }, {}, {})
}