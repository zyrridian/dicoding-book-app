package com.example.bookapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapp.databinding.ItemRecyclerViewBinding

class ListBookAdapter(private val listBook: ArrayList<Book>) :
    RecyclerView.Adapter<ListBookAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, author, publishedYear, genre, description, isbn, pages, coverImage, linkToPurchase, ratings) = listBook[position]
        Glide.with(holder.itemView.context)
            .load(coverImage)
            .error(R.drawable.ic_launcher_background)
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = title
        holder.binding.tvItemAuthor.text = author
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EXTRA_TITLE", title)
                putExtra("EXTRA_AUTHOR", author)
                putExtra("EXTRA_PUBLISHED_YEAR", publishedYear)
                putExtra("EXTRA_GENRE", genre)
                putExtra("EXTRA_DESCRIPTION", description)
                putExtra("EXTRA_ISBN", isbn)
                putExtra("EXTRA_PAGES", pages)
                putExtra("EXTRA_COVER_IMAGE", coverImage)
                putExtra("EXTRA_LINK_TO_PURCHASE", linkToPurchase)
                putExtra("EXTRA_RATINGS", ratings)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    class ListViewHolder(var binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}