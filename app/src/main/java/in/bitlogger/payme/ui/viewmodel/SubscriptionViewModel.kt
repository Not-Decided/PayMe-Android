package `in`.bitlogger.payme.ui.viewmodel

import `in`.bitlogger.payme.Repository.SubscriptionRepository
import `in`.bitlogger.payme.db.Model.Subscription
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
): ViewModel() {

    fun addSub(sub: Subscription) {
        viewModelScope.launch {
            subscriptionRepository.addSubscription(sub)
        }
    }
}