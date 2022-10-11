package com.example.bachelor.presentation.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bachelor.R
import com.example.bachelor.model.User
import com.example.bachelor.presentation.theme.DarkBlue
import com.example.bachelor.presentation.theme.LightBlue


@Composable
fun DetailScreen(user: User) {
    UserDetailCard(user = user)
}
@Composable
fun UserDetailCard(user: User) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, body) = createRefs()
        Header(
            user = user,
            modifier = Modifier
                .constrainAs(header) {
                    linkTo(
                        top = parent.top,
                        start = parent.start,
                        end = parent.end,
                        bottom = body.top
                    )
                    width = Dimension.matchParent
                    height = Dimension.value(250.dp)
                }
        )
        Body(modifier = Modifier
            .padding(20.dp)
            .constrainAs(body) {
                linkTo(
                    top = header.bottom,
                    start = parent.start,
                    end = parent.end,
                    bottom = parent.bottom
                )
                height = Dimension.fillToConstraints
                width = Dimension.matchParent
            }
        )
    }
}

@Composable
fun Header(user: User, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (text, image, background) = createRefs()
        UserDetailsBackground(modifier = Modifier
            .constrainAs(background) {
                linkTo(
                    top = parent.top,
                    start = parent.start,
                    end = parent.end,
                    bottom = parent.bottom,
                    verticalBias = 0f
                )
                width = Dimension.matchParent
                height = Dimension.value(200.dp)
            }
        )
        Text(
            text = user.name,
            modifier = Modifier
                .constrainAs(text) {
                    linkTo(
                        start = background.start,
                        end = background.end,
                        top = background.top,
                        bottom = image.top,
                        topMargin = 80.dp
                    )
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                },
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photoUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_round_account_circle_56),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .constrainAs(image) {
                    linkTo(
                        start = text.start,
                        end = text.end,
                    )
                    top.linkTo(text.bottom, margin = 30.dp)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                }
        )
    }
}

@Composable
fun Body(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Text(text = stringResource(id = R.string.lorem_text))
    }
}


@Composable
fun UserDetailsBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        val height = size.height
        val width = size.width

        val topCurvedBg = height * 0.2f

        val handlePoint = PointF().apply {
            x = width * 0.25f
            y = topCurvedBg
        }

        val pathBg = Path().apply {
            reset()
            moveTo(0f, 0f)
            lineTo(0f, height)
            lineTo(width, height)
            lineTo(width, 0f)
            close()
        }
        val curvedPath = Path().apply {
            moveTo(0f, height)
            lineTo(width, height)
            lineTo(width, topCurvedBg)
            quadraticBezierTo(
                handlePoint.x, handlePoint.y,
                0f, height
            )
            close()
        }

        drawPath(pathBg, color = LightBlue)
        if (height.toDp() > 100.dp) {
            drawPath(curvedPath, color = DarkBlue)
        }
    })
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun UserDetailCardPreview() {
    Surface {
        UserDetailCard(user = User(1, "Detail View", ""))
    }
}