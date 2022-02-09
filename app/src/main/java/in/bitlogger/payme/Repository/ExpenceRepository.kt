package `in`.bitlogger.payme.Repository

import `in`.bitlogger.payme.db.Model.Expence
import `in`.bitlogger.payme.db.Room.ExpenceDao
import `in`.bitlogger.payme.network.ExpenceService
import androidx.lifecycle.LiveData
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class ExpenceRepository @Inject constructor(
    private val expenceDao: ExpenceDao,
    payMeClient: Retrofit
) {

    private val expenceService: ExpenceService = payMeClient.create(ExpenceService::class.java)

    suspend fun addExpence(expence: Expence){
        // Add call for adding expence in mongo.
        expenceDao.addExpence(expence)
        val res = expenceService.addExpence(expence)
        Timber.d("RES ${res.body()}")
    }

    fun getAllExpence(): LiveData<Expence> = expenceDao.getAllExpence()

    fun getAllExpenceSortByDate(): LiveData<Expence> = expenceDao.getAllExpenceByDate()

    fun getSumOfAllExpence(): LiveData<Float> = expenceDao.getSumOfAllExpence()

    suspend fun deleteExpence(expenceId: Long) {
        // Add call for deleting data from mongo.
        expenceDao.deleteExpence(expenceId)
    }

    suspend fun updateExpence(expence: Expence) {
        // Update the data in mongo.
        expenceDao.updateExpence(expence)
    }

    fun getTagsExpenceSum(tag: String) = expenceDao.getTagsExpenceSum(tag)

    fun getAllTags() = expenceDao.getAllTags()
}