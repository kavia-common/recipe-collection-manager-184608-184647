package org.example.app.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.ui.widgets.RecipeAdapter

/**
 PUBLIC_INTERFACE
 SearchFragment allows searching by ingredient keywords and shows filtered results.
 */
class SearchFragment : Fragment() {

    private val vm: SearchViewModel by viewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var input: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recycler)
        input = view.findViewById(R.id.search_input)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = RecipeAdapter { recipe ->
            val bundle = Bundle().apply { putString("recipeId", recipe.id) }
            findNavController().navigate(R.id.detailsFragment, bundle)
        }
        recycler.adapter = adapter

        input.hint = getString(R.string.search_hint)
        input.addTextChangedListener { text ->
            vm.updateQuery(text?.toString().orEmpty())
        }

        vm.results.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // trigger initial load
        vm.updateQuery("")
    }
}
