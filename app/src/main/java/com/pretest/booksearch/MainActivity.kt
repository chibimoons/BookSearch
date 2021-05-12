package com.pretest.booksearch

import android.os.Bundle
import com.pretest.booksearch.databinding.ActivityMainBinding
import com.pretest.search.BookSearchListFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, BookSearchListFragment())
        transaction.commit()
    }
}