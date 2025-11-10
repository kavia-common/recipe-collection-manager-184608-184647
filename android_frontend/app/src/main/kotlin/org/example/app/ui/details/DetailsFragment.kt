package org.example.app.ui.details

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import org.example.app.R
import org.example.app.ui.common.loadUrlOrPlaceholder

/**
 PUBLIC_INTERFACE
 DetailsFragment shows the recipe title, image, ingredients, and instructions,
 and supports marking/unmarking as favorite.
 */
class DetailsFragment : Fragment() {

    private val vm: DetailsViewModel by viewModels()
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var ingredientsList: LinearLayout
    private lateinit var instructionsList: LinearLayout
    private lateinit var favoriteBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title)
        image = view.findViewById(R.id.image)
        ingredientsList = view.findViewById(R.id.ingredients_list)
        instructionsList = view.findViewById(R.id.instructions_list)
        favoriteBtn = view.findViewById(R.id.favorite_btn)

        val recipeId = requireArguments().getString("recipeId") ?: ""

        vm.recipe.observe(viewLifecycleOwner) { r ->
            if (r != null) {
                title.text = r.title
                image.loadUrlOrPlaceholder(r.imageUrl)
                ingredientsList.removeAllViews()
                r.ingredients.forEach { ing ->
                    val tv = TextView(requireContext()).apply {
                        text = "• $ing"
                        setTextColor(resources.getColor(R.color.ocean_text, null))
                        textSize = 16f
                        setPadding(0, 8, 0, 8)
                    }
                    ingredientsList.addView(tv)
                }
                instructionsList.removeAllViews()
                r.instructions.forEachIndexed { idx, step ->
                    val tv = TextView(requireContext()).apply {
                        text = "${idx + 1}. $step"
                        setTextColor(resources.getColor(R.color.ocean_text, null))
                        textSize = 16f
                        setPadding(0, 8, 0, 8)
                    }
                    instructionsList.addView(tv)
                }
            }
        }

        vm.isFavorite.observe(viewLifecycleOwner) { fav ->
            favoriteBtn.text = if (fav) getString(R.string.unmark_favorite) else getString(R.string.mark_favorite)
            favoriteBtn.setBackgroundColor(
                resources.getColor(if (fav) R.color.ocean_secondary else R.color.ocean_primary, null)
            )
        }

        favoriteBtn.setOnClickListener { vm.toggleFavorite() }

        vm.load(recipeId)
    }
}
