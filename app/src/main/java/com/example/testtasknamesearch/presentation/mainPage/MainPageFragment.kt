package com.example.testtasknamesearch.presentation.mainPage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testtasknamesearch.R
import com.example.testtasknamesearch.databinding.FragmentMainPageBinding
import com.example.testtasknamesearch.domain.FavoriteNameInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val IS_VISIBLE = "isVisible"
private const val AGE = "age"

class MainPageFragment : Fragment() {
    private var _binding: FragmentMainPageBinding? = null
    private val binding: FragmentMainPageBinding
        get() = _binding ?: throw RuntimeException("FragmentMainPageBinding didn't find!")

    private val viewModel by lazy {
        ViewModelProvider(this)[MainPageViewModel::class.java]
    }

    private var visibility: Boolean? = null
    private var age: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.apply {
            visibility = getBoolean(IS_VISIBLE)
            age = getString(AGE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        visibility?.let { outState.putBoolean(IS_VISIBLE, it) }
        age?.let { outState.putString(AGE, it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchViewTextParams(view.context)
        setViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViews() {
        if (visibility == true) {
            binding.result.root.visibility = View.VISIBLE
            binding.tvPrediction.visibility = View.GONE
            binding.result.progressBar.visibility = View.GONE
            if (age != null) {
                binding.result.tvAge.text = age.toString()
            }
        }
        binding.result.btnAddToFav.setOnClickListener {
            if (binding.searchView.query.toString().isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val favNameInfo = FavoriteNameInfo(
                        binding.searchView.query.toString(),
                        binding.result.tvAge.text.toString().toInt()
                    )
                    viewModel.addToFavorite(favNameInfo)
                }
            }
        }
        binding.result.btnShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${binding.searchView.query}\n${binding.result.tvAge.text}"
                )
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setSearchViewTextParams(context: Context) {
        val id = binding.searchView.context.resources
            .getIdentifier("android:id/search_src_text", null, null)
        val editText = binding.searchView.findViewById<EditText>(id)
        editText.apply {
            setHintTextColor(ContextCompat.getColor(context, R.color.hint_color))
            typeface = ResourcesCompat.getFont(context, R.font.abeezee)
            textSize = 16f
        }
        editText.setOnEditorActionListener { v, keyAction, _ ->
            if (keyAction == EditorInfo.IME_ACTION_SEARCH) {
                val imm: InputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                visibility = true
                binding.result.root.visibility = View.VISIBLE
                binding.tvPrediction.visibility = View.GONE
                val deferredAge = lifecycleScope.async(Dispatchers.IO) {
                    viewModel.getAgeByName(v.text.toString())
                }
                lifecycleScope.launch {
                    age = deferredAge.await()
                    binding.result.progressBar.visibility = View.GONE
                    if (age != null) {
                        try {
                            binding.result.tvAge.text = age?.toInt().toString()
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), age, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            false
        }
    }
}