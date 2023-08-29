package glailton.io.github.domus.ui.presentation.screens.register

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import glailton.io.github.domus.R
import glailton.io.github.domus.rule.WebServerRule
import glailton.io.github.domus.rule.waitUntil
import glailton.io.github.domus.ui.presentation.navigation.DomusNavigation
import glailton.io.github.domus.ui.presentation.navigation.Routes
import glailton.io.github.domus.ui.theme.DomusTheme
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.getViewModel

@Ignore("Until find a way to test with mock firestore")
class RegisterScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @get:Rule
    val webServerRule = WebServerRule()

    @Test
    fun should_display_all_items_on_register_screen() {
        composeTestRule.setContent {
            DomusTheme {
                RegisterScreen(
                    viewModel = getViewModel(),
                    onRegister = { },
                    onDismissDialog = {},
                    onBack = {}
                )
            }
        }

        composeTestRule.run {
            onNodeWithText(getResourceString(R.string.create_account))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.name))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.email))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.phone_number))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.password))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.confirm_password))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.sign_up))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.already_have_account)+ getResourceString(R.string.login))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.or))
                .assertIsDisplayed()
            onNodeWithText(getResourceString(R.string.login_with))
                .assertIsDisplayed()
            onAllNodes(hasScrollAction())[0]
                .performScrollToNode(hasText(getResourceString(R.string.login_with_facebook)))
            onNodeWithText(getResourceString(R.string.login_with_facebook))
                .assertIsDisplayed()
            onAllNodes(hasScrollAction())[0]
                .performScrollToNode(hasText(getResourceString(R.string.login_with_gmail)))
            onNodeWithText(getResourceString(R.string.login_with_gmail))
                .assertIsDisplayed()
        }
    }

    @Test
    fun should_display_dialog_error_on_register_screen() {
        composeTestRule.setContent {
            DomusTheme {
                DomusNavigation(startDestination = Routes.LoginScreenRoute.route)
            }
        }

        composeTestRule.run {

            onNodeWithContentDescription(getResourceString(R.string.arrow_icon_description))
                .assertIsDisplayed()
                .performClick()

            onNodeWithText(getResourceString(R.string.sign_up))
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