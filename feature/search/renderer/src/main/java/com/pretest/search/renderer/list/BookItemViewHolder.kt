package com.pretest.search.renderer.list

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.pretest.search.domain.entity.Book
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.presentation.list.intent.ClickBook
import com.pretest.search.renderer.R
import com.pretest.search.renderer.base.BaseRecyclerViewHolder
import com.pretest.search.renderer.databinding.BookSearchListItemViewBinding
import com.pretest.search.renderer.utils.DateTimeUtils
import com.pretest.search.renderer.utils.PriceUtils

class BookItemViewHolder(
    binding: BookSearchListItemViewBinding,
    eventListener: (event: BookSearchListIntent) -> Unit?
): BaseRecyclerViewHolder<BookSearchListItemViewBinding, Book, BookSearchListIntent>(binding, eventListener) {

    private lateinit var book: Book

    override fun initViews() {
        viewBinding.root.setOnClickListener { eventListener(ClickBook(book)) }
    }

    override fun bind(data: Book) {
        this.book = data
        updateThumbnail()
        updateTitle()
        updatePublishDate()
        updateDescription()
        updatePrice()
        updateFavorite()
    }

    private fun updateThumbnail() {
        Glide.with(viewBinding.thumbnail)
            .load(book.imageUrl)
            .into(viewBinding.thumbnail)
    }

    private fun updateTitle() {
        viewBinding.title.text = book.title
    }

    private fun updateDescription() {
        viewBinding.description.text = book.description
    }

    private fun updatePrice() {
        viewBinding.price.text = PriceUtils.getPrice(getContext(), book.price)
    }

    private fun updatePublishDate() {
        viewBinding.publishDate.text = DateTimeUtils.getFormattedDateString(book.publishDate)
    }

    private fun updateFavorite() {
        getFavoriteIcon()?.let {
            viewBinding.favorite.setImageDrawable(it)
        }
    }

    private fun getFavoriteIcon(): Drawable? {
        return if (book.like) {
            ContextCompat.getDrawable(getContext(), R.drawable.ico_star_on)
        } else {
            ContextCompat.getDrawable(getContext(), R.drawable.ico_star_off)
        }
    }
}