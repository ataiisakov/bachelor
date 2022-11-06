package com.example.bachelor

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
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
        setContentView(R.layout.activity_main)
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
        detailFragment.enterTransition = Fade()

        val photo = view.findViewById<ImageView>(R.id.userPhotoImageView)
        val name = view.findViewById<TextView>(R.id.userNameTextView)

        homeFragment.exitTransition = Fade()
        homeFragment.reenterTransition = Fade()

        supportFragmentManager
            .commit {
                setReorderingAllowed(true)

                addSharedElement(
                    photo,
                    String.format(
                        resources.getString(R.string.shared_image_transition),
                        user.id
                    )
                )
                addSharedElement(
                    name,
                    String.format(
                        resources.getString(R.string.shared_name_transition),
                        user.id
                    )
                )
                replace(R.id.fragmentContainer, detailFragment)
                addToBackStack(null)
            }
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun App() {
        MyCustomTheme {
            val navController = rememberAnimatedNavController()
            Scaffold { innerPadding ->
                AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}