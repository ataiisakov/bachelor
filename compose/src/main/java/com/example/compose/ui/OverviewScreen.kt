package com.example.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.bachelor.model.User
import com.example.bachelor.model.UserRepository
import com.example.compose.R
import com.example.compose.rememberMetricsStateHolder

@Composable
fun OverviewScreen(onUserClick: (User) -> Unit = {}) {
    UserList(usersList = UserRepository.users, onUserClick)
}


@Composable
fun UserList(usersList: List<User>, onUserClick: (User) -> Unit) {
    val listState = rememberLazyListState()
    // [START compose_jank_metrics]
    val metricsStateHolder = rememberMetricsStateHolder()

    // Reporting scrolling state from compose should be done from side effect to prevent recomposition.
    LaunchedEffect(metricsStateHolder, listState) {
        snapshotFlow { listState.isScrollInProgress }.collect { isScrolling ->
            if (isScrolling) {
                metricsStateHolder.state?.putState("LazyList", "Scrolling")
            } else {
                metricsStateHolder.state?.removeState("LazyList")
            }
        }
    }
    // [END compose_jank_metrics]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("lazyColumn"),
        state = listState
    ) {
        item {
            HeaderFooterListItem("Header Text")
        }
        items(items = usersList, key = { user -> user.id }) { user: User ->
            UserListItem(user = user, onUserClick = onUserClick)
        }
        item {
            HeaderFooterListItem("Footer Text")
        }
    }
}

@Composable
fun HeaderFooterListItem(text: String) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(style = TextStyle(fontSize = 30.sp), text = text)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserListItem(user: User, onUserClick: (User) -> Unit) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(user.photoUrl)
            .crossfade(true)
            .placeholder(R.drawable.ic_round_account_circle_56)
            .transformations(CircleCropTransformation())
            .build()
    )

    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(6.dp),
        onClick = { onUserClick(user) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(56.dp)
            )
            Text(modifier = Modifier.padding(horizontal = 8.dp), text = user.id.toString())
        }
    }
}

@Preview
@Composable
fun UserItemPreview() {
    UserListItem(user = User(1, "", ""), onUserClick = {})
}

@Preview
@Composable
fun UserListPreview() {
    UserList(usersList = UserRepository.users, onUserClick = {})
}