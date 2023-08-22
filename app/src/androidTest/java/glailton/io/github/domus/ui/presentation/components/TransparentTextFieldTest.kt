package glailton.io.github.domus.ui.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import glailton.io.github.domus.R
import glailton.io.github.domus.ui.theme.DomusTheme
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Rule
import org.junit.Test

class TransparentTextFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @SuppressLint("UnrememberedMutableState")
    @Test
    fun should_display_transparent_text_field() {
        composeTestRule.setContent {
            DomusTheme {
                TransparentTextField(
                    textFieldValue = "Test",
                    textLabel = getResourceString(R.string.email),
                    onValueChanged = {},
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                    keyboardActions = KeyboardActions(
                        onNext = {}
                    )
                )
            }
        }

        composeTestRule.run {
            onNodeWithText(getResourceString(R.string.email))
                .assertIsDisplayed()

            onNodeWithText("Test")
                .assertIsDisplayed()

        }
    }
}