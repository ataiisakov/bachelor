package com.example.bachelor

import android.view.View
import com.example.bachelor.model.User

interface Navigator {
    fun showDetails(user: User, view: View?)
}