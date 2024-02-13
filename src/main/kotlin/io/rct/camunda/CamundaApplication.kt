
package io.rct.camunda

import io.camunda.zeebe.spring.client.annotation.Deployment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Camunda Application - Spring - Kotlin - Template
 *
 * @constructor Create empty Camunda application
 */
@SpringBootApplication
@Deployment(resources = ["bpmn/payment_process.bpmn"])
class CamundaApplication

/**
 * Main Spring Run Method
 *
 * @param args
 */
fun main(args: Array<String>) {
    println("Starting Camunda 8 - Spring and Kotlin Template!")
    runApplication<CamundaApplication>(*args)
}



