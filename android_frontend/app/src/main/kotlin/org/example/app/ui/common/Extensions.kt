package org.example.app.ui.common

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.example.app.R

fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun ImageView.loadUrlOrPlaceholder(url: String?) {
    if (url.isNullOrEmpty()) {
        setImageResource(R.drawable.ic_placeholder)
    } else {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(this)
    }
}
