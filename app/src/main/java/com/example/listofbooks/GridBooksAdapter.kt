package com.example.listofbooks

import Books
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.listofbooks.databinding.GridFinancialBookCardBinding
import com.example.listofbooks.databinding.GridKidsBookCardBinding
import com.example.listofbooks.databinding.GridSfBookCardBinding
import com.squareup.picasso.Picasso

class GridBooksAdapter(private val context: Context, private val bookList: ArrayList<Books>) :
    RecyclerView.Adapter<ViewHolder>(), Filterable {

    var booksListFiltered: ArrayList<Books> = ArrayList()

    companion object {
        private const val TYPE_SF = 0
        private const val TYPE_FINANCIAL = 1
        private const val TYPE_KIDS = 2
    }

    override fun getItemViewType(position: Int): Int {
        val book = bookList[position]
        if (book.bookType == "Fictional") return TYPE_SF
        if (book.bookType == "Finance") return TYPE_FINANCIAL
        return if (book.bookType == "Kids") TYPE_KIDS
        else TYPE_SF
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_SF -> {
                val binding = GridSfBookCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GridSfBookViewHolder(binding)
            }

            TYPE_KIDS -> {
                val binding = GridKidsBookCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GridKidsBookViewHolder(binding)
            }

            TYPE_FINANCIAL -> {
                val binding = GridFinancialBookCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GridFinancialBookViewHolder(binding)
            }

            else -> throw Exception("Unknown book type")
        }
    }

    override fun getItemCount(): Int {
        return booksListFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = booksListFiltered[position]
        when (getItemViewType(position)) {
            TYPE_SF -> {
                holder as GridSfBookViewHolder
                Picasso.get().load(book.bookImage).into(holder.imageOfBook)
                holder.binding.bookTitle.text = book.bookName
                Picasso.get().load(book.authorImage).into(holder.imageOfAuthor)
                holder.binding.authorName.text = book.authorName
                holder.binding.isdnName.text = book.isbn
                Picasso.get().load(book.bookTypeImage).into(holder.imageOfType)
                holder.binding.bookType.text = book.bookType
                holder.binding.recycleIcon.setOnClickListener {
                    deleteItem(position)
                }
                holder.binding.Check.setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (compoundButton.isPressed) {
                        book.favorite = isChecked
                        notifyItemChanged(position)
                    }
                }
                holder.checkBox.isChecked = book.favorite
                holder.binding.cardDetails.setOnClickListener {
                    val bookSf = bookList[position]
                    val intent = Intent(context, BookDetailsActivity::class.java)
                    intent.putExtra("BOOK_DETAILS", bookSf)
                    context.startActivity(intent)
                }
            }

            TYPE_KIDS -> {
                holder as GridKidsBookViewHolder
                Picasso.get().load(book.bookImage).into(holder.imageOfBook)
                holder.binding.bookTitle.text = book.bookName
                Picasso.get().load(book.authorImage).into(holder.imageOfAuthor)
                holder.binding.authorName.text = book.authorName
                holder.binding.isdnName.text = book.isbn
                Picasso.get().load(book.bookTypeImage).into(holder.imageOfType)
                holder.binding.bookType.text = book.bookType
                holder.binding.recycleIcon.setOnClickListener {
                    deleteItem(position)
                }
                holder.binding.Check.setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (compoundButton.isPressed) {
                        book.favorite = isChecked
                        notifyItemChanged(position)
                    }
                }
                holder.checkBox.isChecked = book.favorite
                holder.binding.cardDetails.setOnClickListener {
                    val bookKids = bookList[position]
                    val intent = Intent(context, BookDetailsActivity::class.java)
                    intent.putExtra("BOOK_DETAILS", bookKids)
                    context.startActivity(intent)
                }
            }

            TYPE_FINANCIAL -> {
                holder as GridFinancialBookViewHolder
                Picasso.get().load(book.bookImage).into(holder.imageOfBook)
                holder.binding.bookTitle.text = book.bookName
                Picasso.get().load(book.authorImage).into(holder.imageOfAuthor)
                holder.binding.authorName.text = book.authorName
                holder.binding.isdnName.text = book.isbn
                Picasso.get().load(book.bookTypeImage).into(holder.imageOfType)
                holder.binding.bookType.text = book.bookType
                holder.binding.recycleIcon.setOnClickListener {
                    deleteItem(position)
                }
                holder.binding.Check.setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (compoundButton.isPressed) {
                        book.favorite = isChecked
                        notifyItemChanged(position)
                    }
                }
                holder.checkBox.isChecked = book.favorite
                holder.binding.cardDetails.setOnClickListener {
                    val bookFinancial = bookList[position]
                    val intent = Intent(context, BookDetailsActivity::class.java)
                    intent.putExtra("BOOK_DETAILS", bookFinancial)
                    context.startActivity(intent)
                }
            }
        }
    }

    private fun deleteItem(index: Int) {
        booksListFiltered.removeAt(index)
        bookList.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, booksListFiltered.size)
        notifyItemChanged(index, bookList.size)
    }

    class GridKidsBookViewHolder(val binding: GridKidsBookCardBinding) : ViewHolder(binding.root) {
        val imageOfBook = binding.bookImage
        val imageOfAuthor = binding.authorImage
        val imageOfType = binding.typeImage
        val checkBox = binding.Check

    }

    class GridSfBookViewHolder(val binding: GridSfBookCardBinding) : ViewHolder(binding.root) {
        val imageOfBook = binding.bookImage
        val imageOfAuthor = binding.authorImage
        val imageOfType = binding.typeImage
        val checkBox = binding.Check
    }


    class GridFinancialBookViewHolder(val binding: GridFinancialBookCardBinding) :
        ViewHolder(binding.root) {
        val imageOfBook = binding.bookImage
        val imageOfAuthor = binding.authorImage
        val imageOfType = binding.typeImage
        val checkBox = binding.Check

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBooks(books: List<Books>) {
        bookList.clear()
        bookList.addAll(books)
        booksListFiltered.clear()
        booksListFiltered.addAll(books)
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val booksListFiltered = if (charSearch.isEmpty()) {
                    bookList
                } else {
                    val resultList = ArrayList<Books>()
                    for (row in bookList) {
                        if (row.bookName.lowercase()
                                .contains(constraint.toString().lowercase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = booksListFiltered
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                booksListFiltered = results?.values as ArrayList<Books>
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGridLayoutEnabled(enabled: Boolean) {
        var isGridLayoutEnabled = enabled
        notifyDataSetChanged()
    }

}