// HistoryFragment.kt
package com.dicoding.asclepius.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.FragmentHistoryBinding

// Fragment to display the list of cancer analysis history
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set title for the fragment
        activity?.title = "Analyze History"

        // Initialize RecyclerView with HistoryAdapter
        historyAdapter = HistoryAdapter()
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        // Obtain ViewModel instance using ViewModelFactory
        val factory = HistoryFactory.getInstance(requireContext())
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        // Observe history data and submit it to the adapter
        historyViewModel.cancers.observe(viewLifecycleOwner) { cancerList ->
            historyAdapter.submitList(cancerList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clear binding to avoid memory leaks
        _binding = null
    }
}