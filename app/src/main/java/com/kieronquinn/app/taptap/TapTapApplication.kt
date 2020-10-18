package com.kieronquinn.app.taptap

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kieronquinn.app.taptap.services.TapAccessibilityService
import com.kieronquinn.app.taptap.services.TapGestureAccessibilityService
import com.kieronquinn.app.taptap.utils.AppIconRequestHandler
import com.squareup.picasso.Picasso

class TapTapApplication : Application() {

    var accessibilityService: MutableLiveData<TapAccessibilityService?> = MutableLiveData(null)
    var gestureAccessibilityService: MutableLiveData<TapGestureAccessibilityService?> = MutableLiveData(null)

    var disableWake = false

    override fun onCreate() {
        super.onCreate()
        Picasso.setSingletonInstance(Picasso.Builder(this)
                .addRequestHandler(AppIconRequestHandler(this))
                .build())
    }

}