package `in`.bitlogger.payme.di

import `in`.bitlogger.payme.util.Constants.PAYME_DATABASE
import `in`.bitlogger.payme.db.Room.PayMeRoomDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getPayMeDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PayMeRoomDatabase::class.java,
        PAYME_DATABASE
    ).build()

    @Provides
    @Singleton
    fun getExpenceDao(paymeDb: PayMeRoomDatabase) = paymeDb.getExpenceDao()

    @Provides
    @Singleton
    fun getSubscriptionDao(paymeDb: PayMeRoomDatabase) = paymeDb.getSubscriptionDao()
}