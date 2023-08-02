package no.realitylab.arface.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import no.realitylab.arface.R
import no.realitylab.arface.ar_activities.GlassesActivity
import no.realitylab.arface.models.ItemModel

class ItemAdapter(
    private var modelsList : List<ItemModel> = emptyList()
    //private val changeToRV: (Int) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return modelsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(modelsList[position])
        /*
        holder.itemView.setOnClickListener {
            //changeToRV(position)
            val intent = Intent(it.context, GlassesActivity::class.java)
            it.context.startActivity(intent)
        }
        */
    }
}