package `in`.bitlogger.payme.db.Model

import `in`.bitlogger.payme.core.ExpenceType
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Expence(
    @PrimaryKey var _id: Long,
    var amount: Float,
    var message: String,
    var tags: String,
    var date: Long,
    var expenceType: ExpenceType,
    var isSplit: Boolean,
    var split: Split?
)

data class Split(
    var expenceId: Long,
    var splitId: Long,
    var amount: List<Float>,
    var splitName: List<String>,
    var splitUserId: List<Long>,
    var splitUserPhone: List<Long>
)