package `in`.bitlogger.payme.db.Model


data class User(
    var uid: Long,
    var name: String,
    var email: String,
    var phone: Long,
    var dob: String,
    var gender: String
)