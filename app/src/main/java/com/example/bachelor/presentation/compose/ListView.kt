package com.example.bachelor.presentation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bachelor.model.User
import com.example.bachelor.model.UserRepository
import com.example.bachelor.presentation.UserListItem

@Composable
fun UserList(usersList: List<User>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = usersList) { user: User ->
            UserListItem(user = user)
        }
    }
}


@Preview
@Composable
fun UserListPreview() {
    UserList(usersList = UserRepository.users)
}