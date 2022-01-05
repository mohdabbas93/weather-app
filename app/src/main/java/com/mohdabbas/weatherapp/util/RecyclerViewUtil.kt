package com.mohdabbas.weatherapp.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Mohammad Abbas
 * On: 1/5/22.
 */
object RecyclerViewUtil {
    enum class SpaceType { Horizontal, Vertical }

    fun addSpacingDecorationForRecyclerView(
        spaceType: SpaceType,
        space: Int = 15
    ): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                if (spaceType == SpaceType.Horizontal) {
                    outRect.left = space
                    outRect.right = space
                } else if (spaceType == SpaceType.Vertical) {
                    outRect.top = space
                    outRect.bottom = space
                }
            }
        }
    }
}