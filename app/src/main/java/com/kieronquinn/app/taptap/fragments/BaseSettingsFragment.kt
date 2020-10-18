package com.kieronquinn.app.taptap.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.kieronquinn.app.taptap.R
import com.kieronquinn.app.taptap.activities.SettingsActivity
import com.kieronquinn.app.taptap.preferences.Preference
import com.kieronquinn.app.taptap.preferences.SliderPreference
import com.kieronquinn.app.taptap.utils.SHARED_PREFERENCES_NAME
import com.kieronquinn.app.taptap.utils.getToolbarHeight
import com.kieronquinn.app.taptap.utils.isMainEnabled
import com.kieronquinn.app.taptap.utils.isTripleTapEnabled
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.android.synthetic.main.activity_settings.*

abstract class BaseSettingsFragment : PreferenceFragmentCompat(), View.OnScrollChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = SHARED_PREFERENCES_NAME
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.applySystemWindowInsetsToPadding(top = true, bottom = true, left = true, right = true)
        listView.post {
            val topPadding = if(this is SettingsFragment) ((context?.getToolbarHeight() ?: 0) * 2) + resources.getDimension(R.dimen.margin_small).toInt()
            else context?.getToolbarHeight()
            listView.setPadding(listView.paddingLeft, listView.paddingTop + (topPadding ?: 0), listView.paddingRight, listView.paddingBottom)
            listView.setOnScrollChangeListener(this)
            listView.overScrollMode = View.OVER_SCROLL_NEVER
            listView.smoothScrollToPosition( 0)
        }
        setToolbarElevationEnabled(false)
    }

    override fun onResume() {
        super.onResume()
        (activity as? SettingsActivity)?.run {
            if(this@BaseSettingsFragment is SettingsFragment){
                setSwitchVisible(true)
                setSwitchTag(SettingsActivity.TAG_SWITCH_MAIN)
                setSwitchChecked(isMainEnabled)
                setSwitchText(R.string.switch_main)
            }else{
                setSwitchVisible(false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHomeAsUpEnabled(false)
    }

    private fun setToolbarElevationEnabled(enabled: Boolean){
        (activity as? SettingsActivity)?.setToolbarElevationEnabled(enabled)
    }

    internal fun setHomeAsUpEnabled(enabled: Boolean){
        (activity as? SettingsActivity)?.supportActionBar?.apply {
            //Re-apply the icon to fix theme switching
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(enabled)
        }
    }

    override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        setToolbarElevationEnabled(listView.computeVerticalScrollOffset() > 0)
    }

    fun navigate(@IdRes navigationAction: Int, options: Bundle? = null){
        findNavController().navigate(navigationAction, options)
    }

}