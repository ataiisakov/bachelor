package com.example.bachelor

import android.os.Bundle
import android.transition.ChangeImageTransform
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        val changeImageAnimation = ChangeImageTransform()
        changeImageAnimation.duration = 1000
        val detailFragment = DetailFragment.newInstance(user)
        detailFragment.sharedElementEnterTransition = changeImageAnimation
        detailFragment.sharedElementReturnTransition = changeImageAnimation
        val userPhotoImageView = findViewById<View>(R.id.userPhotoImageView)
        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(userPhotoImageView, "userPhoto_${user.id}")
            .replace(R.id.fragmentContainer, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}