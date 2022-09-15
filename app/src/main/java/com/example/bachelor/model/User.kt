package com.example.bachelor.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val name: String,
    val photoUrl: String
): Parcelable
