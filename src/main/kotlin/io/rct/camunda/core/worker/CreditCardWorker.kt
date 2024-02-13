package io.rct.camunda.core.worker

import io.rct.camunda.core.service.CreditCardService
import io.rct.camunda.core.service.exception.CreditCardExpiredException
import io.rct.camunda.core.utils.VariableConstants.CARD_NUMBER
import io.rct.camunda.core.utils.VariableConstants.CREDIT_CARD_ERROR
import io.rct.camunda.core.utils.VariableConstants.CVC
import io.rct.camunda.core.utils.VariableConstants.EXPIRY_DATE
import io.rct.camunda.core.utils.VariableConstants.OPEN_AMOUNT
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.client.api.worker.JobClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import java.lang.Double.valueOf

/**
 * Credit Card Worker - Camunda
 *
 * @property service
 * @constructor Create empty Credit card worker
 */
@Component
class CreditCardWorker (private val service: CreditCardService) {

    private val logger: Logger = getLogger(CreditCardWorker::class.java)

    /**
     * Handle Camunda Job Event - Credit Card Worker
     *
     * @param client
     * @param job
     */
    fun handle(client: JobClient, job: ActivatedJob) {
        logger.info("Handling credit card payment for process instance {}", job.processInstanceKey);

        val variables: Map<String, Any> = job.variablesAsMap
        val cardNumber = variables[CARD_NUMBER].toString()
        val cvc = variables[CVC].toString()
        val expiryDate = variables[EXPIRY_DATE].toString()
        val amount = valueOf(variables[OPEN_AMOUNT].toString())

        try {
            service.chargeAmount(cardNumber, cvc, expiryDate, amount)
            client.newCompleteCommand(job).send()
        } catch (e: CreditCardExpiredException) {
            logger.info("Credit card payment failed: {}", e.localizedMessage)
            val map : Map<String, Any> = mapOf(
                "errorMessage" to e.localizedMessage
            )
            client
                .newThrowErrorCommand(job)
                .errorCode(CREDIT_CARD_ERROR)
                .errorMessage(e.localizedMessage)
                .variables(map)
                .send()
        }
    }

}
