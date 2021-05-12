package com.pretest.search.renderer.base

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0): RecyclerView(context, attrs, defStyle) {

    var onScrollStateListener: ((direction:Int) -> Unit)? = null
    var onScrollTopListener: (() -> Unit)? = null
    var onScrollMiddleListener: (() -> Unit)? = null
    var onScrollBottomListener: (() -> Unit)? = null

    private var scrollDirection: Int = 0

    init {
        init()
    }

    private fun init() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when {
                    isMiddle(recyclerView) -> onScrollMiddleListener?.invoke()
                    isTop(recyclerView) -> onScrollTopListener?.invoke()
                    isBottom(recyclerView) -> onScrollBottomListener?.invoke()
                }
                scrollDirection = if (dy > 0) TOP_DIRECTION else BOTTOM_DIRECTION
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    SCROLL_STATE_DRAGGING -> onScrollStateListener?.invoke(scrollDirection)
                }
            }
        })
    }

    private fun isTop(recyclerView: RecyclerView): Boolean {
        return !recyclerView.canScrollVertically(TOP_DIRECTION)
    }

    private fun isMiddle(recyclerView: RecyclerView): Boolean {
        return recyclerView.canScrollVertically(TOP_DIRECTION) && recyclerView.canScrollVertically(BOTTOM_DIRECTION)
    }

    private fun isBottom(recyclerView: RecyclerView): Boolean {
        return !recyclerView.canScrollVertically(BOTTOM_DIRECTION)
    }

    companion object {
        const val TOP_DIRECTION = -1
        const val BOTTOM_DIRECTION = 1
    }
}