package com.pretest.search.renderer.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewHolder<VIEW_BINDING: ViewBinding, DATA, EVENT>(
    val viewBinding: VIEW_BINDING,
    val eventListener: (event: EVENT) -> Unit?
) : RecyclerView.ViewHolder(viewBinding.root) {

    init {
        initViews()
    }

    abstract fun initViews()

    abstract fun bind(data: DATA)

    fun getContext(): Context {
        return viewBinding.root.context
    }
}