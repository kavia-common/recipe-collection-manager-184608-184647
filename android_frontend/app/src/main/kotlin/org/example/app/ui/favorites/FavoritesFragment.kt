package org.example.app.ui.favorites

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.app.R
import org.example.app.ui.widgets.FavoriteAdapter
import org.example.app.ui.common.show

/**
 PUBLIC_INTERFACE
 FavoritesFragment displays locally persisted favorites.
 */
class FavoritesFragment : Fragment() {

    private val vm: FavoritesViewModel by viewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var empty: TextView
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.recycler)
        empty = view.findViewById(R.id.empty)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = FavoriteAdapter(
            onClick = { fav ->
                val bundle = Bundle().apply { putString("recipeId", fav.id) }
                findNavController().navigate(R.id.detailsFragment, bundle)
            },
            onRemove = { fav ->
                vm.remove(fav.id)
            }
        )
        recycler.adapter = adapter

        vm.favorites.observe(viewLifecycleOwner) { list ->
            empty.show(list.isEmpty())
            adapter.submitList(list)
        }
    }
}
