package io.rct.camunda.core.service.impl

import io.rct.camunda.core.service.CustomerService
import io.rct.camunda.core.service.exception.CustomerNumberFormatException
import io.rct.camunda.core.utils.VariableConstants.DOUBLE_REGEX_PATTERN
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import java.lang.Double.valueOf
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.Pattern.compile
import kotlin.Double.Companion.MIN_VALUE

/**
 * Customer Service Implementation
 *
 * @constructor Create empty Customer Service Implementation
 */
@Service
class CustomerServiceImpl : CustomerService {
    private val logger: Logger = getLogger(CustomerServiceImpl::class.java)
    private val pattern: Pattern = compile(DOUBLE_REGEX_PATTERN)

  /**
   * Deduct the credit for the given customer and the given amount
   *
   * @param customerId
   * @param amount
   * @param credit
   * @return the open order amount
   */
  override fun deductCredit(customerId: String, amount: Double, credit: Double): Double {
    val openAmount: Double
    val deductedCredit: Double
    if (credit > amount) {
      deductedCredit = amount
      openAmount = MIN_VALUE
    } else {
      openAmount = amount - credit
      deductedCredit = credit
    }
    logger.info("charged {} from the credit, open amount is {}", deductedCredit, openAmount)
    return openAmount
  }

  /**
   * Get the current customer credit
   *
   * @param customerId
   * @return the current credit of the given customer
   */
  override fun getCustomerCredit(customerId: String): Double {
    val credit: Double
    val matcher: Matcher = pattern.matcher(customerId)
    if (matcher.matches() && matcher.group(2) != null && matcher.group(2).isNotEmpty()) {
      credit = valueOf(matcher.group(2))
    } else {
      throw CustomerNumberFormatException("The customer ID doesn't end with a number")
    }
    logger.info("customer {} has credit of {}", customerId, credit)

    return credit

  }

}
