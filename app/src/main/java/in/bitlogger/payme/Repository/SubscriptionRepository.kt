package `in`.bitlogger.payme.Repository

import `in`.bitlogger.payme.db.Model.Subscription
import `in`.bitlogger.payme.db.Room.SubscriptionDao
import `in`.bitlogger.payme.network.SubscriptionService
import androidx.lifecycle.LiveData
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val subscriptionDao: SubscriptionDao,
    payMeClient: Retrofit
) {

    private val subService = payMeClient.create(SubscriptionService::class.java)

    suspend fun addSubscription(subscription: Subscription){
        // Add call for adding subscription in mongo.
        subscriptionDao.addSubscription(subscription)
        val res = subService.addSubscription(subscription)
        Timber.d("RES ${res.body()}")
    }

    fun getAllExpence(): LiveData<Subscription> = subscriptionDao.getAllSubscription()

    fun getAllExpenceSortByDate(): LiveData<Subscription> = subscriptionDao.getAllSubscriptionByDate()

    suspend fun deleteExpence(expenceId: Long) {
        // Add call for deleting data from mongo.
        subscriptionDao.deleteSubscription(expenceId)
    }

    suspend fun updateExpence(subscription: Subscription) {
        // Update the subscription in mongo.
        subscriptionDao.updateSubscription(subscription)
    }
}