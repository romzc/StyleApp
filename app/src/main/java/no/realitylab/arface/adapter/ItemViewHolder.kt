package no.realitylab.arface.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.realitylab.arface.R
import no.realitylab.arface.models.ItemModel

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val modelName = view.findViewById<TextView>(R.id.tv_model_name)
    private val modelView = view.findViewById<ImageView>(R.id.iv_model_view)

    fun bind(itemModel: ItemModel) {
        modelName.text = itemModel.itemName
        Picasso.get().load(itemModel.itemPhoto).into(modelView)
    }
}