package `in`.bitlogger.payme.db.Model

import `in`.bitlogger.payme.core.PaymentMethod
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Subscription(
    @PrimaryKey val id: Long,
    val subName: String,
    val subPrice: Float,
    val subRenewIn: String,
    val subCreatedOn: String,
    val subMessage: String,
//    val remindBefore: Date,
//    val tags: List<String>,
//    val includeInExpence: Boolean, // If it should automatically add in total expence.
//    val subWebsite: String,
//    val paymentMethod: PaymentMethod
)