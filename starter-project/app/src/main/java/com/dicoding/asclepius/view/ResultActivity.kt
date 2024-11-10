package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.CancerEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.history.HistoryFactory
import com.dicoding.asclepius.view.history.HistoryViewModel

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding  // View binding for the layout
    private lateinit var historyViewModel: HistoryViewModel  // ViewModel for history
    private var isSaved = false  // Flag to check if result has been saved

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        supportActionBar?.title = "Analyze Result"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = HistoryFactory.getInstance(this)  // ViewModel factory
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        // Displays the image and result passed from the previous activity
        val imageUri: Uri? = intent.getParcelableExtra("IMAGE_URI")
        val resultText: String? = intent.getStringExtra("RESULT_TEXT")

        imageUri?.let {
            binding.resultImage.setImageURI(it)  // Sets the result image
        }

        resultText?.let {
            binding.resultText.text = it  // Sets the result text
        }

        // Save button click listener to save image and result in history
        binding.btnSave.setOnClickListener {
            if (!isSaved) {
                imageUri?.let { uri ->
                    resultText?.let { result ->
                        val cancerEntity = CancerEntity(
                            image = uri.toString(),
                            result = result
                        )

                        // Inserts the result into the history database
                        historyViewModel.insertCancers(listOf(cancerEntity))
                        isSaved = true  // Update flag to prevent duplicate saves

                        Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                        binding.btnSave.isEnabled = false  // Disable save button after saving
                    }
                }
            }
        }
    }

    // Handles the "Up" button press to go back to the previous screen
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()  // Navigates back
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
