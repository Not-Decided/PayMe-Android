package `in`.bitlogger.payme.core

import `in`.bitlogger.payme.Repository.ExpenceRepository
import `in`.bitlogger.payme.Repository.SubscriptionRepository
import `in`.bitlogger.payme.db.Model.Expence
import androidx.lifecycle.LiveData
import javax.inject.Inject

class ManageFinance @Inject constructor(
    private val expenceRepository: ExpenceRepository,
    private val subscriptionRepository: SubscriptionRepository
) {

//    private fun getAllExpence(): LiveData<Expence> = expenceRepository.getAllExpence()

    private fun add(num1: Float, num2: Float) {
        // Adds the new expence in total expence.
    }

    suspend fun addExpence(expence: Expence) {
        // Validate the expence.
        // Adds the expence in the database.
        expenceRepository.addExpence(expence)
    }

    private fun addBudget() {
        // Sets the budget in the preference manager.
    }

    private fun updateExpence() {
        // Updates the current expence.
    }

    private fun deleteExpence() {
        // Deletes the give expence provided by id.
    }

    private fun getBudget() {
        // Returns the expence.
    }

    private fun getAllSubscriptions() {
        // Find all the Recurring expenses and returns it.
    }
}