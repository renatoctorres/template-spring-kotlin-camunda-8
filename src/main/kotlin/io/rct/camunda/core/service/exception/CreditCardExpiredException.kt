package io.rct.camunda.core.service.exception

/**
 * Credit card expired exception
 *
 * @constructor
 *
 * @param message
 */
class CreditCardExpiredException(message: String) : RuntimeException(message)
