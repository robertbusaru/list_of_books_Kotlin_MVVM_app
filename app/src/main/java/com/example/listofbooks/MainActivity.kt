package com.example.listofbooks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listofbooks.databinding.ActivityMainLinearBinding
import com.example.listofbooks.model.BooksViewModel
import com.example.listofbooks.room.BookRoomDatabase
import com.example.listofbooks.room.LocalRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainLinearBinding
    private lateinit var bookAdapter: BooksAdapter
    private lateinit var gridBookAdapter: GridBooksAdapter
    private var isSwitchSelected: Boolean = false
    private lateinit var viewModel: BooksViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var editTextSearch: EditText
    private lateinit var searchButton: Button
    private lateinit var booksDB: BookRoomDatabase
    private lateinit var localRepository: LocalRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLinearBinding.inflate(layoutInflater)
        setContentView(binding.root)
        booksDB = BookRoomDatabase.getDatabase(applicationContext)
        viewModel = ViewModelProvider(this)[BooksViewModel::class.java]
        progressBar = binding.progressBar
        editTextSearch = binding.searchLayout.searchBarEditText
        searchButton = binding.searchLayout.searchBarButton
        editTextSearch = binding.searchLayout.searchBarEditText
        binding.retryLayout.retryButton.setOnClickListener {
            viewModel.getPost()
            setupObserver()
        }

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isSwitchSelected) {
                    gridBookAdapter.filter.filter(s.toString().trim())
                } else bookAdapter.filter.filter(s.toString().trim())
            }
        })

        lifecycleScope.launch {
            val localRepository = LocalRepository(booksDB.getBookDao())
            val numberOfBooks = localRepository.getNumberOfBooks()
            Log.d("main eu", "Number of books: $numberOfBooks")
        }

        viewModel.getPost()
        setupObserver()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        bookAdapter = BooksAdapter(this, arrayListOf())
        gridBookAdapter = GridBooksAdapter(this, arrayListOf())

        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = bookAdapter

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            isSwitchSelected = isChecked
            val context = buttonView.context

            if (isChecked) {
                binding.recycleView.layoutManager = GridLayoutManager(context, 2)
                binding.recycleView.adapter = gridBookAdapter
                binding.switch1.text = getString(R.string.grid_name)
            } else {
                binding.recycleView.layoutManager = LinearLayoutManager(context)
                binding.recycleView.adapter = bookAdapter
                binding.switch1.text = getString(R.string.linear_name)
            }
            gridBookAdapter.setGridLayoutEnabled(isSwitchSelected)
        }
    }

    private fun setupObserver() {
        viewModel.isLoadingScreen.observe(this) { isLoadingScreen ->

            progressBar.visibility = View.VISIBLE
            binding.retryLayout.root.visibility = View.INVISIBLE
            binding.retryLayout.retryButton.visibility = View.VISIBLE

            if (!isLoadingScreen) {
                viewModel.booksListLiveData.observe(this) { booksList ->
                    if (booksList != null) {
                        bookAdapter.addBooks(booksList)
                        gridBookAdapter.addBooks(booksList)
                        insertBooksIntoDb(booksList)
                        binding.retryLayout.root.visibility = View.INVISIBLE
                        progressBar.visibility = View.GONE
                    } else {
                        progressBar.visibility = View.GONE
                        binding.retryLayout.root.visibility = View.VISIBLE
                        binding.retryLayout.retryButton.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun insertBooksIntoDb(books: List<Books>) {
        viewModel.viewModelScope.launch {
            localRepository = LocalRepository(booksDB.getBookDao())
            if (localRepository.getNumberOfBooks() != 0) {
                localRepository.insertBooks(books)
            }
        }
    }

}


