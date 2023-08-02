package no.realitylab.arface.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.realitylab.arface.models.ItemModel
import no.realitylab.arface.models.ItemModelResponse
import no.realitylab.arface.providers.ItemProvider

class ItemViewModel: ViewModel() {
    private val _models = MutableLiveData<List<ItemModel>>()
    val models: MutableLiveData<List<ItemModel>> = _models

    fun updateModels(newModels: List<ItemModel>) {
        _models.value = newModels
    }
}