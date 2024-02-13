package io.rct.camunda.core.model

/**
 * Person Model
 *
 * @property firstName
 * @property lastName
 * @property email
 * @constructor Create empty Person
 */
data class Person(
    val firstName: String,
    val lastName: String,
    val email: String,
)
