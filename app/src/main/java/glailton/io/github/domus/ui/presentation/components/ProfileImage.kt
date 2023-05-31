package glailton.io.github.domus.ui.presentation.components

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
import com.google.firebase.auth.FirebaseUser
import glailton.io.github.domus.ui.theme.Blue500

@Composable
fun ProfileImage(user: FirebaseUser?) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (user?.photoUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.photoUrl)
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
                    text = "GC",
                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                )
            }
        }
    }
}