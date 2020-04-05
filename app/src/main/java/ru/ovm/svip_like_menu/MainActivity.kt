package ru.ovm.svip_like_menu

import android.os.Bundle
import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WithDrawer {

    var drawerOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        button_home.setOnClickListener { changeFragment(HomeFragment()) }
        button_info.setOnClickListener { changeFragment(InfoFragment()) }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
        animateDrawer(close)
    }

    override fun openDrawer() = animateDrawer(open)

    private fun animateDrawer(open: Boolean) {
        TransitionManager.beginDelayedTransition(scene_root)
        val params = fragment_container.layoutParams

        if (open) {
            params.width = (fragment_container.measuredWidth * .8).toInt()
            params.height = (fragment_container.measuredHeight * .8).toInt()
        } else {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        fragment_container.layoutParams = params
        TransitionManager.endTransitions(scene_root)
        drawerOpen = open
    }

    override fun onBackPressed() {
        if (drawerOpen) animateDrawer(close) else super.onBackPressed()
    }

    companion object {
        val close = false
        val open = true
    }
}
