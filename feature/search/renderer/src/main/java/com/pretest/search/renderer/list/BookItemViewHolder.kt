package com.pretest.search.renderer.list

import com.bumptech.glide.Glide
import com.pretest.search.domain.entity.Book
import com.pretest.search.presentation.list.intent.BookSearchListIntent
import com.pretest.search.presentation.list.intent.ClickBook
import com.pretest.search.renderer.R
import com.pretest.search.renderer.base.BaseRecyclerViewHolder
import com.pretest.search.renderer.databinding.BookSearchListItemViewBinding
import com.pretest.search.renderer.utils.DateTimeUtils
import java.text.DecimalFormat

class BookItemViewHolder(
    binding: BookSearchListItemViewBinding,
    eventListener: (event: BookSearchListIntent) -> Unit?
): BaseRecyclerViewHolder<BookSearchListItemViewBinding, Book, BookSearchListIntent>(binding, eventListener) {

    private lateinit var book: Book
    private val priceFormatter: DecimalFormat = DecimalFormat("###,###")

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
        viewBinding.price.text = getContext().getString(R.string.book_price_format, priceFormatter.format(book.price))
    }

    private fun updatePublishDate() {
        viewBinding.publishDate.text = DateTimeUtils.getFormattedDateString(book.publishDate)
    }
}