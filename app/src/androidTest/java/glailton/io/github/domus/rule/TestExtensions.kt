package glailton.io.github.domus.rule

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.text.TextLayoutResult
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import org.junit.Assert.assertEquals

const val TIME_OUT = 8000L

fun SemanticsNodeInteraction.assertBackgroundColor(expectedBackground: Color): SemanticsNodeInteraction {
    val capturedName = captureToImage().colorSpace.name
    assertEquals(expectedBackground.colorSpace.name, capturedName)
    return this
}

fun SemanticsNodeInteraction.assertTextColor(
    color: Color
): SemanticsNodeInteraction = assert(isOfColor(color))

private fun isOfColor(color: Color): SemanticsMatcher = SemanticsMatcher(
    "${SemanticsProperties.Text.name} is of color '$color'"
) {
    val textLayoutResults = mutableListOf<TextLayoutResult>()
    it.config.getOrNull(SemanticsActions.GetTextLayoutResult)
        ?.action
        ?.invoke(textLayoutResults)
    return@SemanticsMatcher if (textLayoutResults.isEmpty()) {
        false
    } else {
        textLayoutResults.first().layoutInput.style.color == color
    }
}

fun ComposeContentTestRule.waitUntil(timeoutMillis: Long = TIME_OUT, nodeWithText: String, size: Int) {
    waitUntil(timeoutMillis = timeoutMillis) {
        onAllNodesWithText(nodeWithText)
            .fetchSemanticsNodes().size == size
    }
}

fun CountingIdlingResource.register() = IdlingRegistry.getInstance().register(this)

fun CountingIdlingResource.unregister() = IdlingRegistry.getInstance().unregister(this)