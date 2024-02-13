package io.rct.camunda.infra.delivery

import io.rct.camunda.core.utils.VariableConstants.PAYMENT_PROCESS
import io.camunda.zeebe.client.ZeebeClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Start form Rest Controller - Start Zeebe Client Process
 *
 * @property client
 * @constructor Create empty Start form Rest Controller - Zeebe Client Process
 */
@RestController
@RequestMapping("/")
class StartFormRestController (private val client: ZeebeClient) {

    private val logger: Logger = getLogger(StartFormRestController::class.java)

    /**
     * Start Process Instance (Payment Process) for Zeebe CLient
     *
     * @param variables
     */
    @PostMapping("/start")
    fun startProcessInstance(@RequestBody variables: Map<String, Any>) {
        logger.info("Starting process `paymentProcess` with variables: $variables")
        client
            .newCreateInstanceCommand()
            .bpmnProcessId(PAYMENT_PROCESS)
            .latestVersion()
            .variables(variables)
            .send()
    }

}
