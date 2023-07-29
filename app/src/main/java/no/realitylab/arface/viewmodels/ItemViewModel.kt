package no.realitylab.arface.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.realitylab.arface.models.ItemModelResponse

class ItemViewModel: ViewModel() {
    private val _models = MutableLiveData<ItemModelResponse>()
    val models: MutableLiveData<ItemModelResponse> = _models

    init {

    }

    fun updateModels(newModels: ItemModelResponse) {
        _models.value = newModels
    }
}