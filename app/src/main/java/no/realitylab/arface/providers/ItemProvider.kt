package no.realitylab.arface.providers

import no.realitylab.arface.models.ItemModel

class ItemProvider {
    companion object {
        val itemModelsList = listOf<ItemModel>(
            ItemModel(1, "Lentes 1", "https://i.ibb.co/HDBbWRP/lente-color.png"),
            ItemModel(2, "Lentes 2", "https://i.ibb.co/HDBbWRP/lente-color.png"),
            ItemModel(3, "Lentes 3", "https://i.ibb.co/HDBbWRP/lente-color.png")
        )
    }
}