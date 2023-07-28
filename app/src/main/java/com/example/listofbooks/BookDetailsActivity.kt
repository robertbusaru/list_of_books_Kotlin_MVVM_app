package com.example.listofbooks

import Books
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.listofbooks.databinding.ActivityCardDetailsBinding
import com.squareup.picasso.Picasso


class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val book = intent?.getSerializableExtra("BOOK_DETAILS") as Books?
        if (book != null) {
            binding.bookTitle.text = book.bookName
            binding.authorName.text = book.authorName
            binding.isdnName.text = book.isbn
            binding.phoneIcon.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:+40757353930")
                startActivity(intent)
            }
            binding.shareToIcon.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, binding.bookTitle.text.toString())
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            binding.goBackIcon.setOnClickListener {
                finish()
            }
            binding.Check.isChecked = book.favorite
            Picasso.get().load(book.bookImage).into(binding.bookImage)
            binding.bookType.text = book.bookType
            Picasso.get().load(book.bookTypeImage).into(binding.typeImage)
            Picasso.get().load(book.authorImage).into(binding.authorImage)
            binding.bookDescription.text = book.description
            binding.bookImage.setOnClickListener {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePictureIntent, 1)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {
                binding.bookImage.setImageBitmap(imageBitmap)
            }
        }
    }
}
