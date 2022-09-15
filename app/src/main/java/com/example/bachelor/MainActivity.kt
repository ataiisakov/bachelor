package com.example.bachelor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bachelor.model.User
import com.example.bachelor.presentation.DetailFragment
import com.example.bachelor.presentation.ListFragment

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, ListFragment())
                .commit()
        }
    }

    override fun showDetails(user: User) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, DetailFragment.newInstance(user))
            .commit()
    }
}