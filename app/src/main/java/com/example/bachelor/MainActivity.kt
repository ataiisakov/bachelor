package com.example.bachelor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.bachelor.model.User
import com.example.bachelor.presentation.DetailFragment
import com.example.bachelor.presentation.ListFragment
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale

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

        detailFragment.sharedElementEnterTransition = MaterialContainerTransform()
        detailFragment.sharedElementReturnTransition = MaterialContainerTransform()

        homeFragment.exitTransition = MaterialElevationScale(false)
        homeFragment.reenterTransition = MaterialElevationScale(true)

        supportFragmentManager
            .commit {
                addSharedElement(
                    view,
                    String.format(
                        resources.getString(R.string.shared_container_transition),
                        user.id
                    )
                )
                replace(R.id.fragmentContainer, detailFragment)
                addToBackStack(null)
            }
    }
}