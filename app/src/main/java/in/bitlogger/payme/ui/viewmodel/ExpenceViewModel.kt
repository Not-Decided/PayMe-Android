package `in`.bitlogger.payme.ui.viewmodel

import `in`.bitlogger.payme.Repository.ExpenceRepository
import `in`.bitlogger.payme.db.Model.Expence
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenceViewModel @Inject constructor(
    private val expenceRepository: ExpenceRepository
): ViewModel(){

    fun addExpence(expence: Expence) {
        viewModelScope.launch {
            expenceRepository.addExpence(expence)
        }
    }

    fun getAllExpenceSum(): LiveData<Float> = expenceRepository.getSumOfAllExpence()

    fun getTagsExpenceSum(tag: String) = expenceRepository.getTagsExpenceSum(tag)

    fun getAllTags() = expenceRepository.getAllTags()
}