package `in`.bitlogger.payme.db.Room

import `in`.bitlogger.payme.db.Model.Expence
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenceDao {

    @Query("SELECT * FROM expence")
    fun getAllExpence(): LiveData<Expence>

    @Insert
    suspend fun addExpence(expence: Expence)

    @Query(" SELECT * FROM expence ORDER BY date")
    fun getAllExpenceByDate(): LiveData<Expence>

    @Query("DELETE FROM expence WHERE _id = :expenceId")
    suspend fun deleteExpence(expenceId: Long)

    // TODO: Check the working of update
    @Update
    suspend fun updateExpence(expence: Expence)

    @Query("SELECT SUM(amount) FROM expence")
    fun getSumOfAllExpence(): LiveData<Float>

    @Query("SELECT DISTINCT tags FROM expence")
    fun getAllTags(): LiveData<List<String>>

    @Query("SELECT SUM(amount) FROM expence WHERE tags = :tag")
    fun getTagsExpenceSum(tag: String): LiveData<Float>
}