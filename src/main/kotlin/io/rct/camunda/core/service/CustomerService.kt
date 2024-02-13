package io.rct.camunda.core.service

interface CustomerService {
    fun deductCredit(customerId: String, amount: Double, credit: Double): Double
    fun getCustomerCredit(customerId: String): Double
}
