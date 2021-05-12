package com.pretest.search.renderer.utils

import android.content.Context
import com.pretest.search.renderer.R
import java.text.DecimalFormat

class PriceUtils {
    companion object {
        private val priceFormatter: DecimalFormat = DecimalFormat("###,###")
        fun getPrice(context: Context, amount: Long): String {
            return context.getString(R.string.book_price_format, priceFormatter.format(amount))
        }
    }
}