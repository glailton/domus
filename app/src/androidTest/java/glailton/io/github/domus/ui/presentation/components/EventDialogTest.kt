package glailton.io.github.domus.ui.presentation.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import glailton.io.github.domus.R
import glailton.io.github.domus.ui.theme.DomusTheme
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Rule
import org.junit.Test

class EventDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_event_dialog() {
        composeTestRule.setContent {
            DomusTheme {
                EventDialog(errorMessage = R.string.error_input_empty)
            }
        }

        composeTestRule.run {
            onNodeWithText(getResourceString(R.string.error))
            onNodeWithText(getResourceString(R.string.error_input_empty))
            onNodeWithText(getResourceString(R.string.ok))
        }
    }
}