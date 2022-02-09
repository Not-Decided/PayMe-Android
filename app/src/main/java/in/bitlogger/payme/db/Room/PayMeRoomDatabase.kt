package `in`.bitlogger.payme.db.Room

import `in`.bitlogger.payme.db.Model.Expence
import `in`.bitlogger.payme.db.Model.Subscription
import `in`.bitlogger.payme.util.TypeConvertors
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [Expence::class, Subscription::class],
    exportSchema = true,
    version = 1
)
@TypeConverters(TypeConvertors::class)
abstract class PayMeRoomDatabase : RoomDatabase() {

    /**
     * Expence DAO include all the expences done by user.
     * */
    abstract fun getExpenceDao(): ExpenceDao

    /**
     * Subscription DAO includes all the data of subscription done by user.
     * */
    abstract fun getSubscriptionDao(): SubscriptionDao
}