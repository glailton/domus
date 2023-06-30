package glailton.io.github.domus.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import glailton.io.github.domus.core.models.User
import glailton.io.github.domus.ui.theme.Blue500

@Composable
fun ProfileImage(user: User?, onClick: () -> Unit) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .clickable { onClick.invoke() }
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (!user?.photo.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user?.photo)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .drawBehind {
                            drawCircle(
                                color = Blue500,
                                radius = this.size.maxDimension
                            )
                        },
                    text = user?.name?.take(2)?.uppercase() ?: "",
                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                )
            }
        }
    }
}