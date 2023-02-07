package com.example.xml

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.commit
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.example.bachelor.model.User
import com.example.xml.databinding.ActivityMainBinding
import com.example.xml.presentation.DetailFragment
import com.example.xml.presentation.ListFragment

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

    override fun showDetails(user: User) {
        val detailFragment = DetailFragment.newInstance(user)

        supportFragmentManager
            .commit {
                setCustomAnimations(
                    R.anim.enter_right_to_left,
                    R.anim.exit_right_to_left,
                    R.anim.enter_left_to_right,
                    R.anim.exit_left_to_right
                )
                replace(R.id.fragmentContainer, detailFragment)
                addToBackStack(null)
            }
    }
}

