package com.example.bookapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val list = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.setHasFixedSize(true)

        list.addAll(getListBooks())
        showRecyclerList()

        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }
    }

    private fun getListBooks(): ArrayList<Book> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataAuthor = resources.getStringArray(R.array.data_author)
        val dataPublishedYear = resources.getStringArray(R.array.data_published_year)
        val dataGenre = resources.getStringArray(R.array.data_genre)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataIsbn = resources.getStringArray(R.array.data_isbn)
        val dataPages = resources.getStringArray(R.array.data_pages)
        val dataCoverImage = resources.getStringArray(R.array.data_cover_image)
        val dataLinkToPurchase = resources.getStringArray(R.array.data_link_to_purchase)
        val dataRatings = resources.getStringArray(R.array.data_ratings)
        val listBook = ArrayList<Book>()
        for (i in dataTitle.indices) {
            val book = Book(
                title = dataTitle[i],
                author = dataAuthor[i],
                description = dataDescription[i],
                coverImage = dataCoverImage[i],
                publishedYear = dataPublishedYear[i],
                genre = dataGenre[i],
                isbn = dataIsbn[i],
                pages = dataPages[i],
                linkToPurchase = dataLinkToPurchase[i],
                ratings = dataRatings[i]
            )
            listBook.add(book)
        }
        return listBook
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerView.layoutManager = GridLayoutManager(this, 5)
        } else {
            binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        }
        val listBookAdapter = ListBookAdapter(list)
        binding.recyclerView.adapter = listBookAdapter
    }

}