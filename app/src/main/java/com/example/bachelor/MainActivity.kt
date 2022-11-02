package com.example.bachelor

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.commit
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.example.bachelor.databinding.ActivityMainBinding
import com.example.bachelor.model.User
import com.example.bachelor.presentation.DetailFragment
import com.example.bachelor.presentation.ListFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var jankStats: JankStats
    private val binding get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    // [START jank_frame_listener]
    private val jankFrameListener = JankStats.OnFrameListener { frameData ->
        // A real app could do something more interesting, like writing the info to local storage and later on report it.
        Log.v(
            "JankStatsSample",
            frameData.toString() + " MillisFrameDuration[${frameData.frameDurationUiNanos / 1e6f}]"
        )
    }
    // [END jank_frame_listener]
    // [END_EXCLUDE]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentContainer, ListFragment())
            }
        }
        val metricsStateHolder = PerformanceMetricsState.getHolderForHierarchy(binding.root)
        jankStats = JankStats.createAndTrack(window, jankFrameListener)
        metricsStateHolder.state?.putState("Activity", javaClass.simpleName)
        binding.root.doOnPreDraw {
            this.reportFullyDrawn()
        }
    }

    override fun onResume() {
        super.onResume()
        jankStats.isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats.isTrackingEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showDetails(user: User, view: View?) {
        view ?: return

        val detailFragment = DetailFragment.newInstance(user)
        val homeFragment = supportFragmentManager.fragments.last()

        detailFragment.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.default_transition)
        detailFragment.sharedElementReturnTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.default_transition)

        val photo = view.findViewById<ImageView>(R.id.iv)
        val name = view.findViewById<TextView>(R.id.tv)

        homeFragment.reenterTransition = Fade().apply { duration = 300 }

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