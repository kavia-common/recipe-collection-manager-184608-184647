package org.example.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.ui.widgets.RecipeAdapter

/**
 PUBLIC_INTERFACE
 HomeFragment displays a scrollable grid of recipes.
 */
class HomeFragment : Fragment() {

    private val vm: HomeViewModel by viewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recycler)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = RecipeAdapter { recipe ->
            val bundle = Bundle().apply { putString("recipeId", recipe.id) }
            findNavController().navigate(R.id.detailsFragment, bundle)
        }
        recycler.adapter = adapter

        vm.recipes.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        vm.load()
    }
}
