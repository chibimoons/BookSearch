package com.pretest.search.renderer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pretest.search.domain.entity.Book
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.renderer.databinding.BookSearchListItemViewBinding

class BookSearchListAdapter(
    private val eventListener: (event: BookSearchListIntent) -> Unit?
): ListAdapter<Book, BookItemViewHolder>(
    getBookSearchListAdapterDiffUtilItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(BookSearchListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false), eventListener)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private fun getBookSearchListAdapterDiffUtilItemCallback() = object : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

}