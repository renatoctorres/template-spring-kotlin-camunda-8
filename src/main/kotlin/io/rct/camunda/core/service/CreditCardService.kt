package io.rct.camunda.core.service

interface CreditCardService {
    fun chargeAmount(cardNumber: String, cvc: String, expiryDate: String, amount: Double)
    fun validateExpiryDate(expiryDate: String): Boolean
}
