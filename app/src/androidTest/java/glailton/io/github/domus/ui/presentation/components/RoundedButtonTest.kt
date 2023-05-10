package glailton.io.github.domus.ui.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import glailton.io.github.domus.R
import glailton.io.github.domus.ui.presentation.utils.TestTags
import glailton.io.github.domus.ui.theme.DomusTheme
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Rule
import org.junit.Test

class RoundedButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_rounded_button() {
        composeTestRule.setContent {
            DomusTheme {
                RoundedButton(
                    text = getResourceString(R.string.login),
                    displayProgressBar = false,
                    onClick = {})
            }
        }

        composeTestRule.run {
            onNodeWithText(getResourceString(R.string.login)).assertIsDisplayed()
            onNodeWithTag(TestTags.LOADING_DIALOG).assertDoesNotExist()
        }
    }

    @Test
    fun should_display_circular_progress_bar() {
        composeTestRule.setContent {
            DomusTheme {
                RoundedButton(
                    text = getResourceString(R.string.login),
                    displayProgressBar = true,
                    onClick = {})
            }
        }

        composeTestRule.run {
            onNodeWithText(getResourceString(R.string.login)).assertDoesNotExist()
            onNodeWithTag(TestTags.LOADING_DIALOG).assertIsDisplayed()
        }
    }
}