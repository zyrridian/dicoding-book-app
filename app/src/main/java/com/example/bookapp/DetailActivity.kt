package com.example.bookapp

import android.animation.Animator
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bookapp.databinding.ActivityDetailBinding
import com.google.android.material.chip.Chip

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var currentAnimator: Animator? = null
    private var shortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the toolbar and enable the back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Setup orientation
        adjustWidthPercentForOrientation()

        // Setup rounded imageview
        binding.bookCoverImage.shapeAppearanceModel = binding.bookCoverImage.shapeAppearanceModel
            .toBuilder()
            .setAllCornerSizes(20f)  // Set corner size as float
            .build()

        // Load data from Intent extras
        val title = intent.getStringExtra("EXTRA_TITLE")
        val author = intent.getStringExtra("EXTRA_AUTHOR")
        val publishedYear = intent.getStringExtra("EXTRA_PUBLISHED_YEAR")
        val genres = intent.getStringExtra("EXTRA_GENRE")
        val description = intent.getStringExtra("EXTRA_DESCRIPTION")
        val isbn = intent.getStringExtra("EXTRA_PUBLISHED_ISBN")
        val pages = intent.getStringExtra("EXTRA_PAGES")
        val coverImage = intent.getStringExtra("EXTRA_COVER_IMAGE")
        val linkToPurchase = intent.getStringExtra("EXTRA_LINK_TO_PURCHASE")
        val ratings = intent.getStringExtra("EXTRA_RATINGS")

        // Bind the data to the UI
        binding.bookTitle.text = title
        binding.bookAuthor.text = author
        binding.bookPublishedYear.text = publishedYear
        binding.bookDescription.text = description
        binding.bookPages.text = pages

        binding.bookLinkToPurchase.setOnClickListener {
            val intent = Intent(this@DetailActivity, WebViewActivity::class.java)
            intent.putExtra("URL", linkToPurchase)
            startActivity(intent)
        }

        Glide.with(this)
            .load(coverImage)
            .into(binding.bookCoverImage)

        if (ratings != null) {
            binding.bookRatings.rating = ratings.toFloat()
        }

        val genreList = genres?.split(", ")
        if (genreList != null) {
            for (tag in genreList) {
                val chip = Chip(this)
                chip.text = tag

                // Create LayoutParams with specific vertical margins
                val params = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 4, 0, 4) // Set top and bottom margins (4 pixels in this case)

                // Apply the LayoutParams to the chip
                chip.layoutParams = params

                chip.setOnCloseIconClickListener {
                    binding.bookGenre.removeView(chip)
                }

                binding.bookGenre.addView(chip)

            }
        }

    }

    // Inflate the menu for the toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareBookDetails()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Function to share book details
    private fun shareBookDetails() {
        val shareText = """
            Check out this book:
            Title: ${binding.bookTitle.text}
            Author: ${binding.bookAuthor.text}
            Description: ${binding.bookDescription.text}
            Link to purchase: ${intent.getStringExtra("EXTRA_LINK_TO_PURCHASE")}
        """.trimIndent()

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    private fun adjustWidthPercentForOrientation() {
        val orientation = applicationContext.resources.configuration.orientation
        val layoutParams = binding.bookCoverImage.layoutParams as ConstraintLayout.LayoutParams
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutParams.matchConstraintPercentWidth = 0.3f
        } else {
            layoutParams.matchConstraintPercentWidth = 0.5f
        }
        binding.bookCoverImage.layoutParams = layoutParams
    }

}