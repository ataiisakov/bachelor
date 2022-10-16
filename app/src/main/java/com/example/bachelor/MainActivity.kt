package com.example.bachelor

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionInflater
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.bachelor.model.User
import com.example.bachelor.presentation.DetailFragment
import com.example.bachelor.presentation.ListFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentContainer, ListFragment())
            }
        }
    }

    override fun showDetails(user: User, view: View?) {
        view ?: return

        val detailFragment = DetailFragment.newInstance(user)
        val homeFragment = supportFragmentManager.fragments.last()

        detailFragment.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.default_transition)
        detailFragment.sharedElementReturnTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.default_transition)
        detailFragment.enterTransition = Slide(Gravity.RIGHT)
        detailFragment.exitTransition = Slide(Gravity.RIGHT)

        val photo = view.findViewById<ImageView>(R.id.iv)
        val name = view.findViewById<TextView>(R.id.tv)

//        homeFragment.exitTransition = Fade()
//        homeFragment.reenterTransition = Fade()

        supportFragmentManager
            .commit {
                setReorderingAllowed(true)
                addSharedElement(
                    photo,
                    resources.getString(R.string.shared_image_transition, user.id)
                )
                addSharedElement(
                    name,
                    resources.getString(R.string.shared_name_transition, user.id)
                )
                replace(R.id.fragmentContainer, detailFragment)
                addToBackStack(null)
            }
    }
}