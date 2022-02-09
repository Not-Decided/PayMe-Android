package `in`.bitlogger.payme.db.Room

import `in`.bitlogger.payme.db.Model.Subscription
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM subscription")
    fun getAllSubscription(): LiveData<Subscription>

    @Insert
    suspend fun addSubscription(subscription: Subscription)

    @Query(" SELECT * FROM subscription ORDER BY subCreatedOn")
    fun getAllSubscriptionByDate(): LiveData<Subscription>

    @Query("DELETE FROM subscription WHERE id = :subscriptionId")
    suspend fun deleteSubscription(subscriptionId: Long)

    // TODO: Check the working of update
    @Update
    suspend fun updateSubscription(subscription: Subscription)
}
