package glailton.io.github.domus.ui.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import glailton.io.github.domus.R
import glailton.io.github.domus.rule.assertBackgroundColor
import glailton.io.github.domus.ui.theme.DomusTheme
import glailton.io.github.domus.ui.theme.FACEBOOKCOLOR
import io.github.kakaocup.kakao.common.utilities.getResourceString
import org.junit.Rule
import org.junit.Test

class SocialMediaButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_social_media_button() {
        composeTestRule.setContent {
            DomusTheme {
                SocialMediaButton(
                    text = getResourceString(R.string.login_with_facebook),
                    onClick = {},
                    socialMediaColor = FACEBOOKCOLOR
                )
            }
        }

        composeTestRule.run {
            onNodeWithText(getResourceString(R.string.login_with_facebook))
                .assertIsDisplayed()
                .assertBackgroundColor(FACEBOOKCOLOR)
        }
    }
}