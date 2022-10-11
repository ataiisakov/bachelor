package com.example.bachelor.presentation.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bachelor.R
import com.example.bachelor.model.User
import com.example.bachelor.model.UserRepository

@Composable
fun OverviewScreen(onUserClick: (User)->Unit = {}) {
    UserList(usersList = UserRepository.users, onUserClick)
}


@Composable
fun UserList(usersList: List<User>, onUserClick: (User) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = usersList) { user: User ->
            UserListItem(user = user, onUserClick = onUserClick)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserListItem(user: User, onUserClick: (User) -> Unit) {
    Card(onClick = { onUserClick(user)} ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.photoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_round_account_circle_56),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
            Text(modifier = Modifier.padding(horizontal = 8.dp), text = user.name)
        }
    }
}
