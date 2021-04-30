package com.example.kotclash.models

import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar


class ResourceBar() {
    //TODO
    var progressbar: ProgressBar? = null
    var resources = 0f
    val speedFill = 1/100f

    fun linkWidget(pro: ProgressBar) {
        progressbar = pro
    }

    fun checkResourceBar(): Float {
        return resources
    }

    fun useResource(price: Int) {
        resources -= price
    }

    fun updateResourceBar(elapsedTimeMS: Long) {
        resources += elapsedTimeMS * speedFill
        Log.wtf("elapse resourcebar", elapsedTimeMS.toString())
        Handler(Looper.getMainLooper()).post(
                Runnable { animResourceBar().start() })


    }

    private fun animResourceBar(): ObjectAnimator {
        val anim = ObjectAnimator.ofInt(progressbar, "progress", resources.toInt())
                .setDuration(1)
        Log.e("animation ", "entered")
        return anim
    }
}
