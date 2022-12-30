package com.example.testtasknamesearch.presentation.favotitesPage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testtasknamesearch.R
import com.example.testtasknamesearch.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoritesPageFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoritesBinding didn't find!")

    private var namesForDeleting = arrayListOf<String>()
    private val viewModel by lazy {
        ViewModelProvider(this)[FavoritePageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRV()
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Удалить имя")
                .setMessage("Вы уверены, что хотите удалить выбранные имена из избранных?")
                .setPositiveButton(
                    R.string.yes
                ) { _, _ ->
                    namesForDeleting.forEach {
                        lifecycleScope.launch(Dispatchers.IO) {
                            viewModel.deleteName(it)
                        }
                    }
                    binding.btnDelete.visibility = View.GONE
                }
                .setNegativeButton(R.string.no, null)
                .show()
        }
    }

    private fun setUpRV() {
        viewModel.favoritesList.observe(viewLifecycleOwner) { list ->
            val favAdapter = FavouritesAdapter(list.map { it.name})
            binding.rvFav.adapter = favAdapter
            favAdapter.onLongClickListener = {
                binding.btnDelete.visibility =
                    if (binding.btnDelete.visibility == View.GONE) View.VISIBLE else View.GONE
            }
            favAdapter.onCheckBoxClickListener = {
                namesForDeleting.add(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}