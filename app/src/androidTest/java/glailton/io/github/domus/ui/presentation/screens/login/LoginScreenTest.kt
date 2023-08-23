package glailton.io.github.domus.ui.presentation.screens.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import glailton.io.github.domus.R
import glailton.io.github.domus.rule.WebServerRule
import glailton.io.github.domus.rule.mockLoginError
import glailton.io.github.domus.rule.waitUntil
import glailton.io.github.domus.ui.presentation.navigation.DomusNavigation
import glailton.io.github.domus.ui.presentation.navigation.Routes
import glailton.io.github.domus.ui.theme.DomusTheme
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.getViewModel

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @get:Rule
    val webServerRule = WebServerRule()

    @Test
    @Ignore("Until find a way to test with mock firestore")
    fun should_display_all_items_on_login_screen() {
        composeTestRule.setContent {
            DomusTheme {
                LoginScreen(
                    viewModel = getViewModel(),
                    onLogin = { },
                    onNavigateToRegister = {},
                    onDismissDialog = {}
                )
            }
        }

        composeTestRule.run {
            onNodeWithContentDescription(getResourceString(R.string.login_description))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.label_welcome))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.login_account))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.email))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.password))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.forgot_password))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.login))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.not_have_account) + getResourceString(R.string.sign_up))
                .assertIsDisplayed()
            onNodeWithContentDescription(getResourceString(R.string.arrow_icon_description))
                .assertIsDisplayed()
        }
    }

    @Test
    fun should_display_firebase_error_password_on_login_screen() {
        webServerRule.mockLoginError()

        composeTestRule.setContent {
            DomusTheme {
                DomusNavigation(startDestination = Routes.LoginScreenRoute.route)
            }
        }

        composeTestRule.run {
            onNodeWithText(getResourceString(R.string.email))
                .assertIsDisplayed()
                .performTextInput("test@email.com")
            onNodeWithText(getResourceString(R.string.password))
                .assertIsDisplayed()
                .performTextInput("123456")
            onNodeWithText(getResourceString(R.string.login))
                .assertIsDisplayed()
                .performClick()

            waitUntil(
                nodeWithText = getResourceString(
                    R.string.error_login_user_not_found
                ), size = 1
            )

            onNodeWithText(getResourceString(R.string.error_login_user_not_found))
                .assertIsDisplayed()
        }
    }

    @Test
    fun should_display_dialog_error_on_login_screen() {
        composeTestRule.setContent {
            DomusTheme {
                DomusNavigation(startDestination = Routes.LoginScreenRoute.route)
            }
        }

        composeTestRule.run {

            onNodeWithText(getResourceString(R.string.login))
                .assertIsDisplayed()
                .performClick()

            waitUntil(
                nodeWithText = getResourceString(
                    R.string.error_input_empty
                ), size = 1
            )

            onNodeWithText(getResourceString(R.string.error_input_empty))
                .assertIsDisplayed()
        }
    }
}