package `in`.bitlogger.payme.util

import `in`.bitlogger.payme.core.ExpenceType
import `in`.bitlogger.payme.db.Model.Split
import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

object TypeConvertors {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromExpenceType(expenceType: ExpenceType): String {
        return if (ExpenceType.ONE_TIME == expenceType) "ONE_TIME" else "RECURRING"
    }

    @TypeConverter
    fun fromExpenceString(expenceType: String): ExpenceType {
        return if (expenceType == "ONE_TIME") ExpenceType.ONE_TIME else ExpenceType.RECURRING
    }

    @TypeConverter
    fun fromSplit(split: Split?): String {
        val gson = Gson()
        return gson.toJson(split)
    }

    @TypeConverter
    fun toSplit(string: String): Split {
        return Gson().fromJson(string, Split::class.java)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }
}