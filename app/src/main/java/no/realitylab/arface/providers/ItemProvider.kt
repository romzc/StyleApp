package no.realitylab.arface.providers

import no.realitylab.arface.models.ItemModel
import no.realitylab.arface.models.ItemModelResponse

class ItemProvider {
    companion object {
        val itemModelsList = ItemModelResponse(
            listOf<ItemModel>(
                ItemModel(1, "Lentes 1", "https://i.ibb.co/HDBbWRP/lente-color.png"),
                ItemModel(2, "Lentes 2", "https://i.ibb.co/HDBbWRP/lente-color.png"),
                ItemModel(3, "Lentes 3", "https://i.ibb.co/HDBbWRP/lente-color.png"),
                ItemModel(4, "Lentes 3", "https://i.ibb.co/HDBbWRP/lente-color.png")
            )
        )
    }
}