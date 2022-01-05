package com.mohdabbas.weatherapp.util

import android.view.View

/**
 * Created by Mohammad Abbas
 * On: 1/4/22.
 */
object ViewVisibilityUtil {
    fun makeVisible(vararg views: View) {
        views.forEach {
            it.visibility = View.VISIBLE
        }
    }

    fun makeGone(vararg views: View) {
        views.forEach {
            it.visibility = View.GONE
        }
    }

    fun makeInvisible(vararg views: View) {
        views.forEach {
            it.visibility = View.INVISIBLE
        }
    }
}