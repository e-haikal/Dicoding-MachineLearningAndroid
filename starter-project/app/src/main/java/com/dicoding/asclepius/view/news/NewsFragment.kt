package com.dicoding.asclepius.view.news

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.FragmentNewsBinding

// Fragment to display list of latest news articles
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Latest News"

        // Setting up ViewModel with factory
        viewModel = ViewModelProvider(this, NewsFactory.getInstance(requireContext()))[NewsViewModel::class.java]

        // Initialize adapter with click listener to open article URLs in browser
        adapter = NewsAdapter { newsUrl ->
            val browserIntent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(newsUrl))
            startActivity(browserIntent)
        }

        // Setting up RecyclerView with adapter and layout manager
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())

        observeViewModel()
    }

    // Observe ViewModel LiveData to update UI based on loading state and article list
    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.listNews.observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles)
        }
    }

    // Show or hide the progress bar based on loading state
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
