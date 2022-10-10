package com.example.bachelor.presentation.compose

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bachelor.R
import com.example.bachelor.model.User
import com.example.bachelor.presentation.theme.DarkBlue
import com.example.bachelor.presentation.theme.LightBlue

@Composable
fun UserDetailCard(user: User) {
    Column {
        UserDetailHeader(user = user)
        UserDetailContentText()
    }

}

@Composable
fun UserDetailHeader(user: User, modifier: Modifier = Modifier) {
    val constrains = ConstraintSet {
        val text = createRefFor("text")
        val img = createRefFor("img")
        val bg = createRefFor("bg")

        constrain(bg) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.value(200.dp)
        }
        constrain(text) {
            top.linkTo(bg.top, margin = 80.dp)
            start.linkTo(bg.start)
            end.linkTo(bg.end)
            bottom.linkTo(img.top)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
        constrain(img) {
            start.linkTo(text.start)
            end.linkTo(text.end)
            top.linkTo(text.bottom, margin = 30.dp)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        createVerticalChain(bg, chainStyle = ChainStyle.Packed(0f))
    }
    ConstraintLayout(constraintSet = constrains, modifier = modifier.fillMaxSize()) {
        UserDetailsBackground(modifier = Modifier.layoutId("bg"))
        Text(
            text = "Something",
            modifier = Modifier.layoutId("text"),
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
                .layoutId("img")
        )
    }
}

@Composable
fun UserDetailContentText(modifier: Modifier = Modifier) {
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

@Preview
@Composable
fun UserDetailsBackgroundPreview() {
    UserDetailHeader(user = User(1, "", ""))
}

//@Preview
@Composable
fun UserDetailCardPreview() {
//    val user = User(1, "", "")
    UserDetailCard(user = User(1, "", ""))
}