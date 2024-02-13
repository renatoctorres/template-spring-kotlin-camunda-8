package io.rct.camunda.core.worker

import io.rct.camunda.core.service.CustomerService
import io.rct.camunda.core.utils.VariableConstants.CUSTOMER_CREDIT_WORKER
import io.rct.camunda.core.utils.VariableConstants.CUSTOMER_ID
import io.rct.camunda.core.utils.VariableConstants.ORDER_TOTAL
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.spring.client.annotation.JobWorker
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component
import java.lang.Double.valueOf

/**
 * Customer Credit Worker - Camunda
 *
 * @property service
 * @constructor Create empty Customer Credit Worker - Camunda
 */
@Component
class CustomerCreditWorker (private val service: CustomerService) {

    private val logger: Logger = getLogger(CustomerCreditWorker::class.java)

    /**
     * Handle Camunda Job Event - Customer Credit
     *
     * @param job
     * @return
     */
    @JobWorker(type = CUSTOMER_CREDIT_WORKER)
    fun handle(job: ActivatedJob): Map<String, Any> {
        logger.info("Handling customer credit for process instance {}", job.processInstanceKey)
        val variables: Map<String, Any> = job.variablesAsMap
        val customerId = variables[CUSTOMER_ID].toString()
        val amount : Double = valueOf(variables[ORDER_TOTAL].toString())
        val customerCredit: Double = service.getCustomerCredit(customerId)
        val remainingAmount: Double = service.deductCredit(customerId, amount, customerCredit)

        return mapOf(
            "customerCredit" to customerCredit,
            "remainingAmount" to remainingAmount
        )
    }

}
