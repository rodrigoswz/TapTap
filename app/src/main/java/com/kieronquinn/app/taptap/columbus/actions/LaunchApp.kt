package com.kieronquinn.app.taptap.columbus.actions

import android.content.Context
import com.kieronquinn.app.taptap.services.TapAccessibilityService
import com.kieronquinn.app.taptap.models.WhenGateInternal
import com.kieronquinn.app.taptap.utils.isAppLaunchable

class LaunchApp(context: Context, private val appPackageName: String, whenGates: List<WhenGateInternal>) : ActionBase(context, whenGates) {

    override fun isAvailable(): Boolean {
        val accessibilityService = context as TapAccessibilityService
        return context.isAppLaunchable(appPackageName) && accessibilityService.getCurrentPackageName() != appPackageName && super.isAvailable()
    }

    override fun onTrigger() {
        super.onTrigger()
        val packageManager = context.packageManager
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(appPackageName)
            context.startActivity(launchIntent)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


}