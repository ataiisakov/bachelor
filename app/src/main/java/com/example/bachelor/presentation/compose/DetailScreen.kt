package com.example.bachelor.presentation.compose

import android.graphics.PointF
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.bachelor.R
import com.example.bachelor.model.User
import com.example.bachelor.presentation.theme.DarkBlue
import com.example.bachelor.presentation.theme.LightBlue
import kotlin.math.min


@Composable
fun DetailScreen(user: User) {
    UserDetailCard(user = user)
}
@Composable
fun UserDetailCard(user: User) {
    val scroll = rememberScrollState(0)
    val big = 200.dp
    val small = 60.dp
    val gap = with(LocalDensity.current) { big.toPx() - small.toPx() }
    val progress = min(scroll.value / gap, 1f)
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, body, fab) = createRefs()
        HeaderMotion(
            user = user,
            modifier = Modifier
                .constrainAs(header) {
                    linkTo(
                        top = parent.top,
                        start = parent.start,
                        end = parent.end,
                        bottom = body.top,
                        verticalBias = 0f
                    )
                    width = Dimension.matchParent
                },
            progress = progress,
            small = small,
            big = big
        )
        Body(
            modifier = Modifier
                .padding(horizontal = 20.dp)
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
                .verticalScroll(scroll)
        )

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(fab) {
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                },
            visible = (progress == 1f)
        ) {
            FloatingActionButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = null)
            }
        }
    }
}

@Composable
fun Body(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.lorem_text))
    }
}


@OptIn(ExperimentalMotionApi::class)
@Composable
fun HeaderMotion(user: User, modifier: Modifier = Modifier, progress: Float, small: Dp, big: Dp) {

    val motionScene = MotionScene {
        val img = createRefFor("profile_image")
        val txt = createRefFor("profile_name")
        val bg = createRefFor("profile_background")
        val from = constraintSet {
            constrain(img) {
                linkTo(
                    start = bg.start,
                    end = bg.end,
                )
                top.linkTo(txt.bottom, margin = 30.dp)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }
            constrain(bg) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = parent.bottom,
                    verticalBias = 0f
                )
                width = Dimension.matchParent
                height = Dimension.value(big)
            }
            constrain(txt) {
                linkTo(
                    start = bg.start,
                    end = bg.end,
                    top = bg.top,
                    bottom = img.top,
                    topMargin = 80.dp
                )
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }
        }
        val to = constraintSet {
            constrain(img) {
                linkTo(
                    top = bg.top,
                    bottom = bg.bottom,
                )
                start.linkTo(bg.start, margin = 10.dp)
                height = Dimension.value(60.dp)
                width = Dimension.value(60.dp)

            }
            constrain(bg) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = parent.bottom,
                    verticalBias = 0f
                )
                height = Dimension.value(small)
                width = Dimension.matchParent
            }
            constrain(txt) {
                linkTo(
                    top = bg.top,
                    bottom = bg.bottom,
                )
                end.linkTo(bg.end)
                scaleX = 0.7f
                scaleY = 0.7f
            }
        }
        defaultTransition(
            from = from,
            to = to
        ) {
            onSwipe = OnSwipe(
                direction = SwipeDirection.Up,
                side = SwipeSide.Bottom,
                anchor = bg
            )
            keyAttributes(txt) {
                frame(85) {
                    scaleX = 0.7f
                    scaleY = 0.7f
                }
            }
        }
    }
    MotionLayout(
        modifier = modifier,
        motionScene = motionScene,
        progress = progress
    ) {
        UserDetailsBackground(
            modifier = Modifier
                .layoutId("profile_background")
        )
        Text(
            text = user.name,
            modifier = Modifier
                .layoutId("profile_name"),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photoUrl)
                .crossfade(true)
                .transformations(CircleCropTransformation())
                .placeholder(R.drawable.ic_round_account_circle_56)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_round_account_circle_56),
            contentDescription = null,
            modifier = Modifier
                .layoutId("profile_image")
        )
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
fun BackgroundPreview() {
    Surface {
        UserDetailCard(user = User(1, "Detail View", ""))
    }
}

