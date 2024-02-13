package io.rct.camunda.core.service.impl

import io.rct.camunda.core.service.CreditCardService
import io.rct.camunda.core.service.exception.CreditCardExpiredException
import io.rct.camunda.core.utils.SubstringConstants.EXPIRY_DATE_LENGTH
import io.rct.camunda.core.utils.SubstringConstants.MONTH_FINAL_INDEX
import io.rct.camunda.core.utils.SubstringConstants.MONTH_START_INDEX
import io.rct.camunda.core.utils.SubstringConstants.TWO_TH_CENTURY
import io.rct.camunda.core.utils.SubstringConstants.YEAR_FINAL_INDEX
import io.rct.camunda.core.utils.SubstringConstants.YEAR_START_INDEX
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Calendar.FEBRUARY
import java.util.Calendar.UNDECIMBER

/**
 * Credit card Service Implementation
 *
 * @constructor Create empty Credit card Service Implementation
 */
@Service
class CreditCardServiceImpl : CreditCardService {

    private val logger: Logger = getLogger(CreditCardServiceImpl::class.java)

    /**
     * Charge amount
     *
     * @param cardNumber
     * @param cvc
     * @param expiryDate
     * @param amount
     */
    override fun chargeAmount(cardNumber: String, cvc: String, expiryDate: String, amount: Double) {
        logger.info(
            "charging card {} that expires on {} and has cvc {} with amount of {}",
            cardNumber,
            expiryDate,
            cvc,
            amount
        )
        if (!validateExpiryDate(expiryDate)) {
            val message = "Expiry date $expiryDate is invalid"
            logger.info("Error message: {}", message)
            throw CreditCardExpiredException(message)
        }
        logger.info("payment completed")
    }

    /**
     * Validate expiry date
     *
     * @param expiryDate
     * @return
     */
    override fun validateExpiryDate(expiryDate: String): Boolean {
        return if (expiryDate.length != EXPIRY_DATE_LENGTH) {
            false
        } else try {
            val month = Integer.valueOf(expiryDate.substring(MONTH_START_INDEX, MONTH_FINAL_INDEX))
            val year = Integer.valueOf(expiryDate.substring(YEAR_START_INDEX, YEAR_FINAL_INDEX)) + TWO_TH_CENTURY
            val now = LocalDate.now()
            if (month < FEBRUARY || month > UNDECIMBER || year < now.year) {
                return false
            }
            year > now.year || year == now.year && month >= now.monthValue
        } catch (e: NumberFormatException) {
            print(e.localizedMessage)
            false
        }

    }

}
